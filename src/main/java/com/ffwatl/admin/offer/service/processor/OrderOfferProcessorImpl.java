package com.ffwatl.admin.offer.service.processor;


import com.ffwatl.admin.offer.dao.OfferDao;
import com.ffwatl.admin.offer.domain.FulfillmentGroupAdjustment;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OrderAdjustment;
import com.ffwatl.admin.offer.domain.OrderItemPriceDetailAdjustment;
import com.ffwatl.admin.offer.service.OfferServiceUtilities;
import com.ffwatl.admin.offer.service.discount.*;
import com.ffwatl.admin.offer.service.type.OfferDiscountType;
import com.ffwatl.admin.offer.service.type.OfferRuleType;
import com.ffwatl.admin.order.dao.OrderItemDao;
import com.ffwatl.admin.order.domain.*;
import com.ffwatl.common.rule.Rule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("order_offer_processor")
public class OrderOfferProcessorImpl extends AbstractBaseProcessor implements OrderOfferProcessor {

    private static final Logger logger = LogManager.getLogger();

    @Resource(name = "promotable_item_factory")
    protected  PromotableItemFactory promotableItemFactory;

    @Resource(name = "order_item_dao")
    private OrderItemDao orderItemDao;

    @Resource(name = "offer_dao")
    private OfferDao offerDao;

    @Resource(name = "offer_service_utilities")
    protected OfferServiceUtilities offerServiceUtilities;


    @Override
    public void filterOrderLevelOffer(PromotableOrder promotableOrder,
                                      List<PromotableCandidateOrderOffer> qualifiedOrderOffers, Offer offer) {
        if (offer.getDiscountType().getType().equals(OfferDiscountType.FIX_PRICE.getType())) {
            logger.warn("Offers of type ORDER may not have a discount type of FIX_PRICE. " +
                    "Ignoring order offer (name=" + offer.getName() + ")");
            return;
        }
        boolean orderLevelQualification = false;

        //Order Qualification
        orderQualification:
        {
            if (couldOfferApplyToOrder(offer, promotableOrder)) {
                orderLevelQualification = true;
                break orderQualification;
            }
            for (PromotableOrderItem orderItem : promotableOrder.getDiscountableOrderItems(offer.isValidOnSale())) {
                if (couldOfferApplyToOrder(offer, promotableOrder, orderItem)) {
                    orderLevelQualification = true;
                    break orderQualification;
                }
            }
            for (PromotableFulfillmentGroup fulfillmentGroup : promotableOrder.getFulfillmentGroups()) {
                if (couldOfferApplyToOrder(offer, promotableOrder, fulfillmentGroup)) {
                    orderLevelQualification = true;
                    break orderQualification;
                }
            }
        }

        //Item Qualification
        if (orderLevelQualification) {
            CandidatePromotionItems candidates = couldOfferApplyToOrderItems(offer,
                    promotableOrder.getDiscountableOrderItems(offer.isValidOnSale()));
            if (candidates.isMatchedQualifier()) {
                PromotableCandidateOrderOffer candidateOffer = createCandidateOrderOffer(promotableOrder,
                        qualifiedOrderOffers, offer);
                candidateOffer.getCandidateQualifiersMap().putAll(candidates.getCandidateQualifiersMap());
            }
        }
    }

    /**
     * Private method which executes the appliesToOrderRules in the Offer to determine if this offer
     * can be applied to the Order, OrderItem, or FulfillmentGroup.
     *
     * @return true if offer can be applied, otherwise false
     */
    protected boolean couldOfferApplyToOrder(Offer offer, PromotableOrder promotableOrder, PromotableOrderItem orderItem) {
        return couldOfferApplyToOrder(offer, promotableOrder, orderItem, null);
    }

    /**
     * Private method which executes the appliesToOrderRules in the Offer to determine if this offer
     * can be applied to the Order, OrderItem, or FulfillmentGroup.
     *
     * @return true if offer can be applied, otherwise false
     */
    protected boolean couldOfferApplyToOrder(Offer offer, PromotableOrder promotableOrder,
                                             PromotableFulfillmentGroup fulfillmentGroup) {
        return couldOfferApplyToOrder(offer, promotableOrder, null, fulfillmentGroup);
    }

    /**
     * Private method which executes the appliesToOrderRules in the Offer to determine if this offer
     * can be applied to the Order, OrderItem, or FulfillmentGroup.
     *
     * @return true if offer can be applied, otherwise false
     */
    protected boolean couldOfferApplyToOrder(Offer offer, PromotableOrder promotableOrder,
                                           PromotableOrderItem promotableOrderItem,
                                           PromotableFulfillmentGroup promotableFulfillmentGroup) {
        boolean appliesToItem;
        Rule rule;
        Map<String, Rule> rules = offer.getMatchRules();

        if (rules == null || rules.size() < 1) {
            return true;
        } else {
            rule = rules.get(OfferRuleType.ORDER.getType());
        }

        appliesToItem = rule == null || checkObjectMeetBoundValue(rule, promotableOrder.getOrder());

        if(!appliesToItem && promotableOrderItem != null){
            appliesToItem = checkObjectMeetBoundValue(rule, promotableOrderItem.getOrderItem());

            if(!appliesToItem && promotableFulfillmentGroup != null){
                appliesToItem = checkObjectMeetBoundValue(rule, promotableFulfillmentGroup.getFulfillmentGroup());
            }
        }

        return appliesToItem;
    }

    private PromotableCandidateOrderOffer createCandidateOrderOffer(PromotableOrder promotableOrder,
                                                                    List<PromotableCandidateOrderOffer> qualifiedOrderOffers,
                                                                    Offer offer) {
        PromotableCandidateOrderOffer promotableCandidateOrderOffer = promotableItemFactory.createPromotableCandidateOrderOffer(promotableOrder, offer);
        qualifiedOrderOffers.add(promotableCandidateOrderOffer);

        return promotableCandidateOrderOffer;
    }

    @Override
    public boolean couldOfferApplyToOrder(Offer offer, PromotableOrder promotableOrder) {
        return couldOfferApplyToOrder(offer, promotableOrder, null, null);
    }

    @Override
    public List<PromotableCandidateOrderOffer> removeTrailingNotCombinableOrderOffers(List<PromotableCandidateOrderOffer> candidateOffers) {
        List<PromotableCandidateOrderOffer> remainingCandidateOffers = new ArrayList<>();
        int offerCount = 0;

        for (PromotableCandidateOrderOffer candidateOffer : candidateOffers) {
            if (offerCount == 0) {
                remainingCandidateOffers.add(candidateOffer);
            } else {
                if (candidateOffer.getOffer().isCombinableWithOtherOffers() ||
                        !candidateOffer.getOffer().isTotalitarianOffer()) {
                    remainingCandidateOffers.add(candidateOffer);
                }
            }
            offerCount++;
        }
        return remainingCandidateOffers;
    }

    /**
     * Private method used by applyAllOrderOffers to create an OrderAdjustment from a CandidateOrderOffer
     * and associates the OrderAdjustment to the Order.
     *
     * @param orderOffer a CandidateOrderOffer to apply to an Order
     */
    protected void applyOrderOffer(PromotableOrder promotableOrder, PromotableCandidateOrderOffer orderOffer) {
        PromotableOrderAdjustment promotableOrderAdjustment = promotableItemFactory.createPromotableOrderAdjustment(orderOffer, promotableOrder);
        promotableOrder.addCandidateOrderAdjustment(promotableOrderAdjustment);
    }

    /**
     * Called when the system must determine whether to apply order or item adjustments.
     */
    protected void compareAndAdjustOrderAndItemOffers(PromotableOrder promotableOrder) {
        int orderAdjustmentTotal = promotableOrder.calculateOrderAdjustmentTotal();
        int itemAdjustmentTotal = promotableOrder.calculateItemAdjustmentTotal();

        if (orderAdjustmentTotal >= itemAdjustmentTotal) {
            promotableOrder.removeAllCandidateItemOfferAdjustments();
        } else {
            promotableOrder.removeAllCandidateOrderOfferAdjustments();
        }
    }

    @Override
    public void applyAllOrderOffers(List<PromotableCandidateOrderOffer> orderOffers, PromotableOrder promotableOrder) {
        // If order offer is not combinable, first verify order adjustment is zero, if zero, compare item discount total vs this offer's total
        for (PromotableCandidateOrderOffer orderOffer : orderOffers) {
            if (promotableOrder.canApplyOrderOffer(orderOffer)) {
                applyOrderOffer(promotableOrder, orderOffer);

                if (orderOffer.isTotalitarian() || promotableOrder.isTotalitarianItemOfferApplied()) {
                    if (logger.isTraceEnabled()) {
                        logger.trace("Totalitarian Order Offer Applied. Comparing order and item offers " +
                                "for best outcome.");
                    }
                    compareAndAdjustOrderAndItemOffers(promotableOrder);
                    // We continue because this could be the first offer and marked as totalitarian, but not as good as an
                    // item offer. There could be other order offers that are not totalitarian that also qualify.
                    continue;
                }

                if (!orderOffer.isCombinable()) {
                    if (logger.isTraceEnabled()) {
                        logger.trace("Non-Combinable Order Offer Applied with id=[" +
                                orderOffer.getOffer().getId() + "].  No other order offers can be applied");
                    }
                    break;
                }
            }
        }
        promotableOrder.getOrder().setSubTotal(promotableOrder.calculateSubtotalWithAdjustments());
    }

    @Override
    public PromotableItemFactory getPromotableItemFactory() {
        return promotableItemFactory;
    }

    @Override
    public void setPromotableItemFactory(PromotableItemFactory promotableItemFactory) {
        this.promotableItemFactory = promotableItemFactory;
    }

    private Map<Long, PromotableOrderAdjustment> buildPromotableOrderAdjustmentsMap(PromotableOrder promotableOrder) {
        Map<Long, PromotableOrderAdjustment> adjustmentsMap = new HashMap<>();
        for (PromotableOrderAdjustment adjustment : promotableOrder.getCandidateOrderAdjustments()) {
            adjustmentsMap.put(adjustment.getOffer().getId(), adjustment);
        }
        return adjustmentsMap;
    }

    private void synchronizeOrderAdjustments(PromotableOrder promotableOrder) {
        Order order = promotableOrder.getOrder();

        if (order.getOrderAdjustments().isEmpty() && promotableOrder.getCandidateOrderAdjustments().isEmpty()) {
            return;
        }

        Map<Long, PromotableOrderAdjustment> newAdjustmentsMap = buildPromotableOrderAdjustmentsMap(promotableOrder);
        Iterator<OrderAdjustment> orderAdjIterator = order.getOrderAdjustments().iterator();

        while (orderAdjIterator.hasNext()) {
            OrderAdjustment adjustment = orderAdjIterator.next();
            if (adjustment.getOffer() != null) {
                Long offerId = adjustment.getOffer().getId();
                PromotableOrderAdjustment promotableAdjustment = newAdjustmentsMap.remove(offerId);
                if (promotableAdjustment != null) {
                    if (!(adjustment.getAdjustmentValue() == promotableAdjustment.getAdjustmentValue())) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Updating value for order adjustment with offer Id " + offerId + " to " +
                                    promotableAdjustment.getAdjustmentValue());
                        }
                        adjustment.setAdjustmentValue(promotableAdjustment.getAdjustmentValue());
                    }
                } else {
                    // No longer using this order adjustment, remove it.
                    orderAdjIterator.remove();
                }
            }
        }

        for (PromotableOrderAdjustment promotableOrderAdjustment : newAdjustmentsMap.values()) {
            // Add the newly introduced adjustments.
            Offer offer = promotableOrderAdjustment.getOffer();
            OrderAdjustment orderAdjustment = offerDao.createOrderAdjustment();

            orderAdjustment.init(order, offer, offer.getName());
            orderAdjustment.setAdjustmentValue(promotableOrderAdjustment.getAdjustmentValue());

            order.getOrderAdjustments().add(orderAdjustment);
        }
    }

    private Map<String, PromotableOrderItemPriceDetail> buildPromotableDetailsMap(PromotableOrderItem item) {
        Map<String, PromotableOrderItemPriceDetail> detailsMap = new HashMap<>();
        for (PromotableOrderItemPriceDetail detail : item.getPromotableOrderItemPriceDetails()) {
            detailsMap.put(detail.buildDetailKey(), detail);
        }
        return detailsMap;
    }

    private String buildItemPriceDetailKey(OrderItemPriceDetail itemDetail) {
        List<Long> offerIds = new ArrayList<>();
        for (OrderItemPriceDetailAdjustment adjustment : itemDetail.getOrderItemPriceDetailAdjustments()) {
            Long offerId = adjustment.getOffer().getId();
            offerIds.add(offerId);
        }
        Collections.sort(offerIds);
        return itemDetail.getOrderItem().toString() + offerIds.toString();
    }

    private void processMatchingDetails(OrderItemPriceDetail itemDetail,
                                        PromotableOrderItemPriceDetail promotableItemDetail) {
        Map<Long, OrderItemPriceDetailAdjustment> itemAdjustmentMap =
                offerServiceUtilities.buildItemDetailAdjustmentMap(itemDetail);

        if (itemDetail.getQuantity() != promotableItemDetail.getQuantity()) {
            itemDetail.setQuantity(promotableItemDetail.getQuantity());
        }

        for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableItemDetail.getCandidateItemAdjustments()) {
            OrderItemPriceDetailAdjustment itemAdjustment = itemAdjustmentMap.get(adjustment.getOfferId());

            if (!(itemAdjustment.getAdjustmentValue() == adjustment.getAdjustmentValue())) {
                itemAdjustment.setAdjustmentValue(adjustment.getAdjustmentValue());
                itemAdjustment.setAppliedToSalePrice(adjustment.isAppliedToSalePrice());
            }
        }
    }

    private void synchronizeItemPriceDetails(OrderItem orderItem, PromotableOrderItem promotableOrderItem) {
        Map<String, PromotableOrderItemPriceDetail> promotableDetailsMap = buildPromotableDetailsMap(promotableOrderItem);
        Map<Long, OrderItemPriceDetail> unmatchedDetailsMap = new HashMap<>();

        for (OrderItemPriceDetail orderItemPriceDetail : orderItem.getOrderItemPriceDetails()) {
            String detailKey = buildItemPriceDetailKey(orderItemPriceDetail);
            PromotableOrderItemPriceDetail promotableDetail = promotableDetailsMap.remove(detailKey);
            if (promotableDetail != null) {
                processMatchingDetails(orderItemPriceDetail, promotableDetail);
            } else {
                unmatchedDetailsMap.put(orderItemPriceDetail.getId(), orderItemPriceDetail);
            }
        }

        Iterator<OrderItemPriceDetail> unmatchedDetailsIterator = unmatchedDetailsMap.values().iterator();

        for (PromotableOrderItemPriceDetail priceDetail : promotableDetailsMap.values()) {
            if (unmatchedDetailsIterator.hasNext()) {
                // Reuse an existing priceDetail
                OrderItemPriceDetail existingDetail = unmatchedDetailsIterator.next();

                offerServiceUtilities.updatePriceDetail(existingDetail, priceDetail);
                unmatchedDetailsIterator.remove();
            } else {
                // Create a new priceDetail
                OrderItemPriceDetail newPriceDetail = orderItemDao.createOrderItemPriceDetail();
                newPriceDetail.setOrderItem(orderItem);
                offerServiceUtilities.updatePriceDetail(newPriceDetail, priceDetail);
                orderItem.getOrderItemPriceDetails().add(newPriceDetail);
            }
        }

        // Remove any unmatched details
        Iterator<OrderItemPriceDetail> pdIterator = orderItem.getOrderItemPriceDetails().iterator();
        offerServiceUtilities.removeUnmatchedPriceDetails(unmatchedDetailsMap, pdIterator);
    }

    private void synchronizeOrderItems(PromotableOrder promotableOrder) {
        Order order = promotableOrder.getOrder();
        Map<OrderItem, PromotableOrderItem> promotableItemMap = offerServiceUtilities.buildPromotableItemMap(promotableOrder);

        List<OrderItem> orderItemList = offerServiceUtilities.buildOrderItemList(order);

        /*for (OrderItem orderItem : orderItemList) {
            PromotableOrderItem promotableItem = promotableItemMap.get(orderItem);
            if (promotableItem == null) {
                continue;
            }
            synchronizeItemPriceDetails(orderItem, promotableItem);
            synchronizeItemQualifiers(orderItem, promotableItem);

        }*/
    }

    private Map<Long, PromotionQualifier> buildPromotableQualifiersMap(PromotableOrderItem item) {
        Map<Long, PromotionQualifier> qualifiersMap = new HashMap<>();
        for (PromotableOrderItemPriceDetail detail : item.getPromotableOrderItemPriceDetails()) {
            for (PromotionQualifier qualifier : detail.getPromotionQualifiers()) {
                PromotionQualifier existingQualifier = qualifiersMap.get(qualifier.getPromotion().getId());
                if (existingQualifier != null) {
                    existingQualifier.setQuantity(existingQualifier.getQuantity() + qualifier.getQuantity());
                } else {
                    qualifiersMap.put(qualifier.getPromotion().getId(), qualifier);
                }
            }
        }
        return qualifiersMap;
    }

    private void synchronizeItemQualifiers(OrderItem orderItem, PromotableOrderItem promotableOrderItem) {
        Map<Long, PromotionQualifier> qualifiersMap = buildPromotableQualifiersMap(promotableOrderItem);
        Map<Long, OrderItemQualifier> unmatchedQualifiersMap = new HashMap<>();

        for (OrderItemQualifier orderItemQualifier : orderItem.getOrderItemQualifiers()) {
            PromotionQualifier promotableQualifier = qualifiersMap.remove(orderItemQualifier.getOffer().getId());
            if (promotableQualifier != null) {
                // Offer was used as a qualifier on previous run.   Update quantity if needed.
                if (orderItemQualifier.getQuantity() != promotableQualifier.getQuantity()) {
                    orderItemQualifier.setQuantity(promotableQualifier.getQuantity());
                }
            } else {
                unmatchedQualifiersMap.put(orderItemQualifier.getId(), orderItemQualifier);
            }
        }

        Iterator<OrderItemQualifier> unmatchedQualifiersIterator = unmatchedQualifiersMap.values().iterator();

        for (PromotionQualifier qualifier : qualifiersMap.values()) {
            if (unmatchedQualifiersIterator.hasNext()) {
                // Reuse an existing qualifier
                OrderItemQualifier existingQualifier = unmatchedQualifiersIterator.next();
                existingQualifier.setOffer(qualifier.getPromotion());
                existingQualifier.setQuantity(qualifier.getQuantity());
                unmatchedQualifiersIterator.remove();
            } else {
                // Create a new qualifier
                OrderItemQualifier newQualifier = orderItemDao.createOrderItemQualifier();
                newQualifier.setOrderItem(orderItem);
                newQualifier.setOffer(qualifier.getPromotion());
                newQualifier.setQuantity(qualifier.getQuantity());
                orderItem.getOrderItemQualifiers().add(newQualifier);
            }
        }

        // Remove any unmatched qualifiers
        Iterator<OrderItemQualifier> qIterator = orderItem.getOrderItemQualifiers().iterator();
        offerServiceUtilities.removeUnmatchedQualifiers(unmatchedQualifiersMap, qIterator);
    }

    private Map<Long, PromotableFulfillmentGroup> buildPromotableFulfillmentGroupMap(PromotableOrder order) {
        Map<Long, PromotableFulfillmentGroup> fgMap = new HashMap<>();
        for (PromotableFulfillmentGroup fg : order.getFulfillmentGroups()) {
            fgMap.put(fg.getFulfillmentGroup().getId(), fg);
        }
        return fgMap;
    }

    private Map<Long, PromotableFulfillmentGroupAdjustment> buildPromFulfillmentAdjMap(PromotableFulfillmentGroup fg) {
        Map<Long, PromotableFulfillmentGroupAdjustment> fgMap = new HashMap<>();
        for (PromotableFulfillmentGroupAdjustment adjustment : fg.getCandidateFulfillmentGroupAdjustments()) {
            fgMap.put(adjustment.getPromotableCandidateFulfillmentGroupOffer().getOffer().getId(), adjustment);
        }
        return fgMap;
    }

    private void synchronizeFulfillmentGroupAdjustments(FulfillmentGroup fg, PromotableFulfillmentGroup promotableFG) {
        Iterator<FulfillmentGroupAdjustment> adjustmentIterator = fg.getFulfillmentGroupAdjustments().iterator();
        Map<Long, PromotableFulfillmentGroupAdjustment> promotableAdjMap = buildPromFulfillmentAdjMap(promotableFG);

        // First try and update existing adjustment records
        while (adjustmentIterator.hasNext()) {
            FulfillmentGroupAdjustment currentAdj = adjustmentIterator.next();
            PromotableFulfillmentGroupAdjustment newAdj = promotableAdjMap.remove(currentAdj.getOffer().getId());
            if (newAdj != null) {
                if (!(currentAdj.getAdjustmentValue() == newAdj.getAdjustmentValue())) {
                    // Update the currentAdj.
                    currentAdj.setAdjustmentValue(newAdj.getAdjustmentValue());
                }
            } else {
                // Removing no longer valid adjustment
                adjustmentIterator.remove();
            }
        }

        // Now add missing adjustments
        for (PromotableFulfillmentGroupAdjustment newAdj : promotableAdjMap.values()) {
            FulfillmentGroupAdjustment fa = offerDao.createFulfillmentGroupAdjustment();
            fa.setFulfillmentGroup(fg);
            fa.init(fg, newAdj.getPromotableCandidateFulfillmentGroupOffer().getOffer(), null);
            fa.setAdjustmentValue(newAdj.getAdjustmentValue());
            fg.getFulfillmentGroupAdjustments().add(fa);
        }

    }

    private void synchronizeFulfillmentGroups(PromotableOrder promotableOrder) {
        Order order = promotableOrder.getOrder();
        Map<Long, PromotableFulfillmentGroup> fgMap = buildPromotableFulfillmentGroupMap(promotableOrder);
        FulfillmentGroup fg = order.getFulfillmentGroup();

        synchronizeFulfillmentGroupAdjustments(fg, fgMap.get(fg.getId()));

    }

    @Override
    public void synchronizeAdjustmentsAndPrices(PromotableOrder promotableOrder) {
        synchronizeOrderAdjustments(promotableOrder);
        synchronizeOrderItems(promotableOrder);
        if (extensionManager != null) {
            extensionManager.getProxy().synchronizeAdjustmentsAndPrices(promotableOrder);
        }
        synchronizeFulfillmentGroups(promotableOrder);
    }

    @Override
    public void setOfferDao(OfferDao offerDao) {
        this.offerDao = offerDao;
    }

    @Override
    public void setOrderItemDao(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }
}