package com.ffwatl.admin.offer.service.processor;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.service.FulfillmentGroupOfferPotential;
import com.ffwatl.admin.offer.service.FulfillmentGroupOfferPotentialImpl;
import com.ffwatl.admin.offer.service.discount.*;
import com.ffwatl.admin.offer.service.type.OfferRuleType;
import com.ffwatl.common.rule.Rule;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.collections.comparators.ReverseComparator;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("fulfillment_group_offer_processor")
public class FulfillmentGroupOfferProcessorImpl extends OrderOfferProcessorImpl implements FulfillmentGroupOfferProcessor {


    @Override
    public void filterFulfillmentGroupLevelOffer(PromotableOrder order, List<PromotableCandidateFulfillmentGroupOffer> qualifiedFGOffers, Offer offer) {
        for (PromotableFulfillmentGroup fulfillmentGroup : order.getFulfillmentGroups()) {
            boolean fgLevelQualification = false;
            fgQualification: {
                /*// handle legacy fields in addition to the 1.5 order rule field
                if (couldOfferApplyToOrder(offer, order, fulfillmentGroup)) {
                    fgLevelQualification = true;
                    break fgQualification;
                }*/
                for (PromotableOrderItem discreteOrderItem : order.getAllOrderItems()) {
                    if (couldOfferApplyToOrder(offer, order, discreteOrderItem, fulfillmentGroup)) {
                        fgLevelQualification = true;
                        break fgQualification;
                    }
                }
            }
            if (fgLevelQualification) {
                fgLevelQualification = false;
                // handle 1.5 FG field
                if (couldOfferApplyToFulfillmentGroup(offer, fulfillmentGroup)) {
                    fgLevelQualification = true;
                }
            }

            // Item Qualification - new for 1.5!
            if (fgLevelQualification) {

                CandidatePromotionItems candidates = couldOfferApplyToOrderItems(offer, fulfillmentGroup.getDiscountableOrderItems());
                // couldn't qualify based on the items within this fulfillment group, jump out and now try to validate based
                // on all the items in the order across all fulfillment groups (not the default behavior)
                if (!candidates.isMatchedQualifier() /*&& getQualifyGroupAcrossAllOrderItems(fulfillmentGroup)*/) {
                    candidates = couldOfferApplyToOrderItems(offer, order.getAllOrderItems());
                }

                if (candidates.isMatchedQualifier()) {
                    PromotableCandidateFulfillmentGroupOffer candidateOffer = createCandidateFulfillmentGroupOffer(offer, qualifiedFGOffers, fulfillmentGroup);
                    candidateOffer.getCandidateQualifiersMap().putAll(candidates.getCandidateQualifiersMap());
                }
            }
        }
    }

    protected PromotableCandidateFulfillmentGroupOffer createCandidateFulfillmentGroupOffer(Offer offer, List<PromotableCandidateFulfillmentGroupOffer> qualifiedFGOffers, PromotableFulfillmentGroup fulfillmentGroup) {
        PromotableCandidateFulfillmentGroupOffer promotableCandidateFulfillmentGroupOffer =
                promotableItemFactory.createPromotableCandidateFulfillmentGroupOffer(fulfillmentGroup, offer);
        qualifiedFGOffers.add(promotableCandidateFulfillmentGroupOffer);

        return promotableCandidateFulfillmentGroupOffer;
    }

    /*
    /**
     * Whether or not items across the entire order should be considered in item-level qualifiers for the given fulfillment
     * group. Default behavior is to use only the items within the fulfillment group for the item-level qualifiers.
     *
     * @param fg the fulfillment group that we are attempting to apply item-level qualifiers to
     * @return
     */
    /*
    protected boolean getQualifyGroupAcrossAllOrderItems(PromotableFulfillmentGroup fg) {
        return BLCSystemProperty.resolveBooleanSystemProperty("promotion.fulfillmentgroup.qualifyAcrossAllOrderItems", false);
    }*/

    protected boolean couldOfferApplyToFulfillmentGroup(Offer offer, PromotableFulfillmentGroup fulfillmentGroup) {
        boolean appliesToItem;

        Rule rule;
        Map<String, Rule> rules = offer.getMatchRules();

        if (rules == null || rules.size() < 1) {
            return true;
        }else {
            rule = offer.getMatchRules().get(OfferRuleType.FULFILLMENT_GROUP.getType());
        }
        appliesToItem = rule == null || checkObjectMeetBoundValue(rule, fulfillmentGroup.getFulfillmentGroup());

        return appliesToItem;
    }

    @Override
    public void calculateFulfillmentGroupTotal(PromotableOrder order) {
        int totalFulfillmentCharges = 0;
        for (PromotableFulfillmentGroup fulfillmentGroupMember : order.getFulfillmentGroups()) {
            int fulfillmentCharges = fulfillmentGroupMember.getFinalizedPriceWithAdjustments();

            fulfillmentGroupMember.getFulfillmentGroup().setFulfillmentPrice(fulfillmentCharges);
            totalFulfillmentCharges += fulfillmentCharges;
        }
        order.setTotalFufillmentCharges(totalFulfillmentCharges);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean applyAllFulfillmentGroupOffers(List<PromotableCandidateFulfillmentGroupOffer> qualifiedFGOffers,
                                                  PromotableOrder order) {

        Map<FulfillmentGroupOfferPotential, List<PromotableCandidateFulfillmentGroupOffer>> offerMap = new HashMap<>();

        // grouping all the "PromotableCandidateFulfillmentGroupOffer" by Offer
        // as the result we have Map<Offer, List<PromotableCandidateFulfillmentGroupOffer>>
        for (PromotableCandidateFulfillmentGroupOffer candidate : qualifiedFGOffers) {

            FulfillmentGroupOfferPotential potential = new FulfillmentGroupOfferPotentialImpl();
            potential.setOffer(candidate.getOffer());

            List<PromotableCandidateFulfillmentGroupOffer> list = offerMap.get(potential);
            if (list == null) {
                list = new ArrayList<>();
                offerMap.put(potential, list);
            }
            list.add(candidate);
        }

        List<FulfillmentGroupOfferPotential> potentials = new ArrayList<>();

        for (FulfillmentGroupOfferPotential potential : offerMap.keySet()) {
            List<PromotableCandidateFulfillmentGroupOffer> fgOffers = offerMap.get(potential);

            Collections.sort(fgOffers, new ReverseComparator(new BeanComparator("discountedAmount", new NullComparator())));

            // we need to leave only one fulfillmentGroupOffer with highest discountedAmount value
            leaveOnlyOneResultInList(fgOffers, 0);

            PromotableCandidateFulfillmentGroupOffer candidate = fgOffers.get(0);

            int priceBeforeAdjustments = candidate.getFulfillmentGroup().calculatePriceWithoutAdjustments();
            int discountedPrice = candidate.getDiscountedPrice();

            potential.setTotalSavings(potential.getTotalSavings() + (priceBeforeAdjustments - discountedPrice));

            potentials.add(potential);
        }
        // Sort fg potentials by discount
        Collections.sort(potentials, new BeanComparator("totalSavings", Collections.reverseOrder()));

        /*leaveOnlyOneResultInList(potentials, 0);*/

        return checkAndFilterOffersForTotalirianAndCombinable(potentials, order, offerMap);
    }

    @Override
    public List<FulfillmentGroupOfferPotential> removeTrailingNotCombinableFulfillmentGroupOffers(List<FulfillmentGroupOfferPotential> candidateOffers) {
        List<FulfillmentGroupOfferPotential> remainingCandidateOffers = new ArrayList<>();
        int offerCount = 0;
        for (FulfillmentGroupOfferPotential candidateOffer : candidateOffers) {
            if (offerCount == 0) {
                remainingCandidateOffers.add(candidateOffer);
            } else if(candidateOffer.getOffer().isCombinableWithOtherOffers() ||
                    !candidateOffer.getOffer().isTotalitarianOffer()){
                remainingCandidateOffers.add(candidateOffer);
            }
            offerCount++;
        }
        return remainingCandidateOffers;
    }

    private void leaveOnlyOneResultInList(List list, int index){
        if(list == null || index < 0) return;
        try{
            for (int j = 0; j < list.size(); j++) {
                if(j != index) list.remove(j);
            }
        }catch (IndexOutOfBoundsException e){
            // do nothing
        }
    }

    private boolean checkAndFilterOffersForTotalirianAndCombinable(List<FulfillmentGroupOfferPotential> potentials, PromotableOrder order,
                                                                   Map<FulfillmentGroupOfferPotential, List<PromotableCandidateFulfillmentGroupOffer>> offerMap){
        boolean fgOfferApplied = false;
        for (FulfillmentGroupOfferPotential potential : potentials) {
            Offer offer = potential.getOffer();
            boolean alreadyContainsTotalitarianOffer = order.isTotalitarianOfferApplied();
            List<PromotableCandidateFulfillmentGroupOffer> candidates = offerMap.get(potential);

            for (PromotableCandidateFulfillmentGroupOffer candidate : candidates) {
                applyFulfillmentGroupOffer(candidate.getFulfillmentGroup(), candidate);
                fgOfferApplied = true;
            }

            for (PromotableFulfillmentGroup fg : order.getFulfillmentGroups()) {
                fg.chooseSaleOrRetailAdjustments();
            }

            if (offer.isTotalitarianOffer() || alreadyContainsTotalitarianOffer) {
                fgOfferApplied = compareAndAdjustFulfillmentGroupOffers(order, fgOfferApplied);
                if (fgOfferApplied) {
                    break;
                }
            } else if (!offer.isCombinableWithOtherOffers()) {
                break;
            }
        }

        return fgOfferApplied;
    }

    protected void applyFulfillmentGroupOffer(PromotableFulfillmentGroup promotableFulfillmentGroup,
                                              PromotableCandidateFulfillmentGroupOffer fulfillmentGroupOffer) {
        if (promotableFulfillmentGroup.canApplyOffer(fulfillmentGroupOffer)) {
            PromotableFulfillmentGroupAdjustment promotableFulfillmentGroupAdjustment = promotableItemFactory.createPromotableFulfillmentGroupAdjustment(fulfillmentGroupOffer, promotableFulfillmentGroup);
            promotableFulfillmentGroup.addCandidateFulfillmentGroupAdjustment(promotableFulfillmentGroupAdjustment);
        }
    }

    protected boolean compareAndAdjustFulfillmentGroupOffers(PromotableOrder order, boolean fgOfferApplied) {
        int regularOrderDiscountShippingTotal = order.calculateSubtotalWithoutAdjustments();

        for (PromotableFulfillmentGroup fg : order.getFulfillmentGroups()) {
            regularOrderDiscountShippingTotal += fg.getFinalizedPriceWithAdjustments();
        }

        int discountOrderRegularShippingTotal = order.calculateSubtotalWithAdjustments();
        for (PromotableFulfillmentGroup fg : order.getFulfillmentGroups()) {
            discountOrderRegularShippingTotal += fg.calculatePriceWithoutAdjustments();
        }

        if (discountOrderRegularShippingTotal < regularOrderDiscountShippingTotal) {
            // order/item offer is better; remove totalitarian fulfillment group offer and process other fg offers
            order.removeAllCandidateFulfillmentOfferAdjustments();
            fgOfferApplied = false;
        } else {
            // totalitarian fg offer is better; remove all order/item offers
            order.removeAllCandidateOrderOfferAdjustments();
            order.removeAllCandidateItemOfferAdjustments();
            order.getOrder().setSubTotal(order.calculateSubtotalWithAdjustments());
        }
        return fgOfferApplied;
    }

}