package com.ffwatl.admin.offer.service.processor;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.service.type.OfferRuleType;
import com.ffwatl.admin.offer.service.OfferServiceExtensionManager;
import com.ffwatl.admin.offer.service.type.OfferType;
import com.ffwatl.admin.offer.service.discount.CandidatePromotionItems;
import com.ffwatl.admin.offer.service.discount.PromotableOrderItem;
import com.ffwatl.admin.offer.service.discount.PromotableOrderItemPriceDetail;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.rule.Rule;
import com.ffwatl.common.rule.ValueType;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author ffw_ATL
 */
public abstract class AbstractBaseProcessor implements BaseProcessor {

    private static final Logger logger = LogManager.getLogger();

    @Resource(name = "offer_timezone_processor")
    private OfferTimeZoneProcessor offerTimeZoneProcessor;

    @Resource(name = "offer_service_extension_manager")
    protected OfferServiceExtensionManager extensionManager;

    protected CandidatePromotionItems couldOfferApplyToOrderItems(Offer offer, List<PromotableOrderItem> promotableOrderItems) {
        CandidatePromotionItems candidates = new CandidatePromotionItems();
        if (offer.getMatchRules() == null || offer.getMatchRules().size() == 0) {
            candidates.setMatchedQualifier(true);
        } else {
            for (Rule rule : offer.getMatchRules().values()) {
                if (rule != null) {
                    checkForItemRequirements(offer, candidates, rule, promotableOrderItems, true);
                    if (!candidates.isMatchedQualifier()) {
                        break;
                    }
                }
            }
        }

        if (offer.getType().equals(OfferType.ORDER_ITEM) && offer.getMatchRules() != null) {
            for (Rule rule : offer.getMatchRules().values()) {
                checkForItemRequirements(offer, candidates, rule, promotableOrderItems, false);
                if (!candidates.isMatchedTarget()) {
                    break;
                }
            }
        }

        if (candidates.isMatchedQualifier()) {
            if (! meetsItemQualifierSubtotal(offer, candidates)) {
                candidates.setMatchedQualifier(false);
            }
        }

        return candidates;
    }

    private boolean isEmpty(Collection<?> collection) {
        return (collection == null || collection.size() == 0);
    }

    private void checkForItemRequirements(Offer offer, CandidatePromotionItems candidates, Rule rule,
                                          List<PromotableOrderItem> promotableOrderItems, boolean isQualifier) {
        boolean matchFound = false;

        if (rule != null) {
            // If matches are found, add the candidate items to a list and store it with the itemCriteria
            // for this promotion.
            for (PromotableOrderItem item : promotableOrderItems) {
                if (couldOrderItemMeetOfferRequirement(rule, item)) {
                    if (isQualifier) {
                        candidates.addQualifier(rule, item);
                    } else {
                        candidates.addTarget(rule, item);
                    }
                    matchFound = true;
                }
            }
        }

        if (isQualifier) {
            candidates.setMatchedQualifier(matchFound);
        } else {
            candidates.setMatchedTarget(matchFound);
        }
    }

    private boolean couldOrderItemMeetOfferRequirement(Rule rule, PromotableOrderItem promotableOrderItem) {
        boolean appliesToItem;

        if (rule != null && rule.getFieldName().trim().length() > 0) {
            HashMap<String, Object> vars = new HashMap<>();
            promotableOrderItem.updateRuleVariables(vars);

            OrderItem orderItem = promotableOrderItem.getOrderItem();

            appliesToItem = checkObjectMeetBoundValue(rule, orderItem);

        } else {
            appliesToItem = true;
        }

        return appliesToItem;
    }

    private boolean meetsItemQualifierSubtotal(Offer offer, CandidatePromotionItems candidateItem) {
        int qualifyingSubtotal = offer.getQualifyingItemSubTotal();
        if (qualifyingSubtotal < 1) {
            if (logger.isTraceEnabled()) {
                logger.trace("Offer " + offer.getName() + " does not have an item subtotal requirement.");
            }
            return true;
        }

        if (isEmpty(offer.getMatchRules().values())) {
            if (OfferType.ORDER_ITEM.equals(offer.getType())) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Offer " + offer.getName() + " has a subtotal item requirement but no item qualification criteria.");
                }
                return false;
            } else {
                // Checking if targets meet subtotal for item offer with no item criteria.
                int accumulatedTotal = 0;

                for (PromotableOrderItem orderItem : candidateItem.getAllCandidateTargets()) {
                    int itemPrice = orderItem.getCurrentBasePrice() * orderItem.getQuantity();
                    accumulatedTotal += itemPrice;

                    if (accumulatedTotal > qualifyingSubtotal) {
                        if (logger.isTraceEnabled()) {
                            logger.trace("Offer " + offer.getName() + " meets qualifying item subtotal.");
                        }
                        return true;
                    }
                }
            }

            if (logger.isDebugEnabled()) {
                logger.debug("Offer " + offer.getName() + " does not meet qualifying item subtotal.");
            }
        } else {
            if (candidateItem.getCandidateQualifiersMap() != null) {
                int accumulatedTotal = 0;
                Set<PromotableOrderItem> usedItems = new HashSet<>();

                for (Rule rule : candidateItem.getCandidateQualifiersMap().keySet()) {
                    List<PromotableOrderItem> promotableItems = candidateItem
                            .getCandidateQualifiersMap()
                            .get(rule);

                    if (promotableItems != null) {
                        for (PromotableOrderItem item : promotableItems) {
                            if (!usedItems.contains(item)) {
                                usedItems.add(item);

                                int itemPrice = item.getCurrentBasePrice() * item.getQuantity();
                                accumulatedTotal += itemPrice;

                                if (accumulatedTotal > qualifyingSubtotal) {
                                    if (logger.isTraceEnabled()) {
                                        logger.trace("Offer " + offer.getName() + " meets the item subtotal requirement.");
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        if (logger.isTraceEnabled()) {
            logger.trace("Offer " + offer.getName() + " does not meet the item subtotal qualifications.");
        }
        return false;

    }


    /**
     * We were not able to meet all of the ItemCriteria for a promotion, but some of the items were
     * marked as qualifiers or targets.  This method removes those items from being used as targets or
     * qualifiers so they are eligible for other promotions.
     */
    protected void clearAllNonFinalizedQuantities(List<PromotableOrderItemPriceDetail> priceDetails) {
        for (PromotableOrderItemPriceDetail priceDetail : priceDetails) {
            priceDetail.clearAllNonFinalizedQuantities();
        }
    }

    /**
     * Updates the finalQuanties for the PromotionDiscounts and PromotionQualifiers.
     * Called after we have confirmed enough qualifiers and targets for the promotion.
     */
    protected void finalizeQuantities(List<PromotableOrderItemPriceDetail> priceDetails) {
        for (PromotableOrderItemPriceDetail priceDetail : priceDetails) {
            priceDetail.finalizeQuantities();
        }
    }

    /**
     * Checks to see if the discountQty matches the detailQty.   If not, splits the
     * priceDetail.
     */
    protected void splitDetailsIfNecessary(List<PromotableOrderItemPriceDetail> priceDetails) {
        for (PromotableOrderItemPriceDetail priceDetail : priceDetails) {
            PromotableOrderItemPriceDetail splitDetail = priceDetail.splitIfNecessary();
            if (splitDetail != null) {
                priceDetail.getPromotableOrderItem().getPromotableOrderItemPriceDetails().add(splitDetail);
            }
        }
    }

    @Override
    public List<Offer> filterOffers(List<Offer> offers, User customer) {
        List<Offer> filteredOffers = new ArrayList<>();

        if (offers != null && !offers.isEmpty()) {
            filteredOffers = removeOutOfDateOffers(offers);
            filteredOffers = removeTimePeriodOffers(filteredOffers);
            filteredOffers = removeInvalidCustomerOffers(filteredOffers, customer);
        }
        return filteredOffers;
    }

    /**
     * Removes all offers that are not within the timezone and timeperiod of the offer.
     * If an offer does not fall within the timezone or timeperiod rule,
     * that offer will be removed.
     *
     * @return List of Offers within the timezone or timeperiod of the offer
     */
    private List<Offer> removeTimePeriodOffers(List<Offer> offers) {
        List<Offer> offersToRemove = new ArrayList<>();

        for (Offer offer : offers) {
            if (!couldOfferApplyToTimePeriod(offer)) {
                offersToRemove.add(offer);
            }
        }
        // remove all offers in the offersToRemove list from original offers list
        for (Offer offer : offersToRemove) {
            offers.remove(offer);
        }
        return offers;
    }

    private boolean couldOfferApplyToTimePeriod(Offer offer) {
        boolean appliesToTimePeriod = false;
        String boundValue = null;

        OfferTimeZoneProcessor offerTimeZoneProcessor = getOfferTimeZoneProcessor();
        Rule rule = offer.getMatchRules().get(OfferRuleType.TIME.getType());

        if (rule != null && rule.getBoundValue() != null) {
            boundValue = rule.getBoundValue();
        }

        if (boundValue != null) {
            TimeZone timeZone = offerTimeZoneProcessor.getTimeZone(offer);
            LocalTime current = LocalTime.now(timeZone.toZoneId());
            LocalTime endTime = LocalTime.parse(boundValue, offerTimeZoneProcessor.getDateTimeFormatter());

            if (endTime.isAfter(current)) {
                appliesToTimePeriod = true;
            }
        } else {
            appliesToTimePeriod = true;
        }

        return appliesToTimePeriod;
    }

    /**
     * Removes all out of date offers.  If an offer does not have a start date, or the start
     * date is a later date, that offer will be removed.  Offers without a start date should
     * not be processed.  If the offer has a end date that has already passed, that offer
     * will be removed.  Offers without a end date will be processed if the start date
     * is prior to the transaction date.
     *
     * @param offers all of the offers
     * @return List of Offers with valid dates
     */
    protected List<Offer> removeOutOfDateOffers(List<Offer> offers){
        List<Offer> offersToRemove = new ArrayList<>();
        for (Offer offer : offers) {
            TimeZone timeZone = getOfferTimeZoneProcessor().getTimeZone(offer);

            LocalDateTime current = LocalDateTime.now(timeZone.toZoneId());
            LocalDateTime startDate = null;
            LocalDateTime endDate = null;

            if (offer.getStartDate() != null) {
                startDate = LocalDateTime.ofInstant(offer.getStartDate().toInstant(), timeZone.toZoneId());
                if (logger.isTraceEnabled()) {
                    logger.debug("Offer: " + offer.getName() + " timeZone:" + timeZone +
                            " startTime:" + startDate.toString() + " currentTime:" + current.toString());
                }
            }

            if (offer.getEndDate() != null) {
                endDate = LocalDateTime.ofInstant(offer.getEndDate().toInstant(), timeZone.toZoneId());

                if (logger.isTraceEnabled()) {
                    logger.debug("Offer: " + offer.getName() + " endTime:" + endDate.toString());
                }
            }
            if ((offer.getStartDate() == null) || (startDate != null && startDate.isAfter(current))) {
                offersToRemove.add(offer);
            } else if (offer.getEndDate() != null && (endDate != null && endDate.isBefore(current))) {
                offersToRemove.add(offer);
            }
        }
        // remove all offers in the offersToRemove list from original offers list
        offersToRemove.forEach(offers::remove);
        return offers;
    }

    /**
     * Private method that takes in a list of Offers and removes all Offers from the list that
     * does not apply to this customer.
     *
     * @return List of Offers that apply to this customer
     */
    private List<Offer> removeInvalidCustomerOffers(List<Offer> offers, User customer){
        if(customer == null) return  offers;

        List<Offer> offersToRemove = new ArrayList<>();
        for (Offer offer : offers) {
            if (!couldOfferApplyToCustomer(offer, customer)) {
                offersToRemove.add(offer);
            }
        }
        // remove all offers in the offersToRemove list from original offers list
        offersToRemove.forEach(offers::remove);
        return offers;
    }

    /**
     * Private method which executes the appliesToCustomerRules in the Offer to determine if this Offer
     * can be applied to the Customer.
     *
     * @return true if offer can be applied, otherwise false
     */
    protected boolean couldOfferApplyToCustomer(Offer offer, User customer) {
        boolean appliesToCustomer;
        boolean excluded = false;
        boolean emailEquals;
        Map<String, Rule> rules = offer.getMatchRules();
        String email = null;

        if (rules == null || rules.size() < 1) {
            return true;
        } else {
            Rule rule = rules.get(OfferRuleType.CUSTOMER.getType());

            if (rule != null) {
                email = rule.getBoundValue();
                excluded = rule.isExcluded();
            }
        }
        emailEquals = !StringUtils.isEmpty(email) && email.equals(customer.getEmail());

        appliesToCustomer = StringUtils.isEmpty(email) || (emailEquals && !excluded) || (!emailEquals && excluded);

        return appliesToCustomer;
    }

    protected boolean checkObjectMeetBoundValue(Rule rule, Object object){
        try {
            Class<?> clazz = object.getClass();
            Field field = clazz.getDeclaredField(rule.getFieldName());
            field.setAccessible(true);

            ValueType valueType = rule.getFieldType();

            switch (valueType) {
                case NUMBER: {
                    return field.getDouble(object) >= Double.valueOf(rule.getBoundValue());
                }
                case STRING: {
                    return field.get(object).equals(rule.getBoundValue());
                }
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.warn(e.getMessage());
            logger.warn(e.getCause().toString());
            e.printStackTrace();
        }
        return false;
    }

    public OfferTimeZoneProcessor getOfferTimeZoneProcessor() {
        return offerTimeZoneProcessor;
    }

    public void setOfferTimeZoneProcessor(OfferTimeZoneProcessor offerTimeZoneProcessor) {
        this.offerTimeZoneProcessor = offerTimeZoneProcessor;
    }

}