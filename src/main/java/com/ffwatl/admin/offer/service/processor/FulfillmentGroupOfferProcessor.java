package com.ffwatl.admin.offer.service.processor;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.service.discount.PromotableCandidateFulfillmentGroupOffer;
import com.ffwatl.admin.offer.service.discount.PromotableOrder;

import java.util.List;

public interface FulfillmentGroupOfferProcessor {

    void filterFulfillmentGroupLevelOffer(PromotableOrder order, List<PromotableCandidateFulfillmentGroupOffer> qualifiedFGOffers, Offer offer);

    void calculateFulfillmentGroupTotal(PromotableOrder order);

    /**
     * Private method that takes a list of sorted CandidateOrderOffers and determines if each offer can be
     * applied based on the restrictions (stackable and/or combinable) on that offer.  OrderAdjustments
     * are create on the Order for each applied CandidateOrderOffer.  An offer with stackable equals false
     * cannot be applied to an Order that already contains an OrderAdjustment.  An offer with combinable
     * equals false cannot be applied to the Order if the Order already contains an OrderAdjustment.
     *
     * @param qualifiedFGOffers a sorted list of CandidateOrderOffer
     * @param order the Order to apply the CandidateOrderOffers
     * @return true if order offer applied; otherwise false
     */
    boolean applyAllFulfillmentGroupOffers(List<PromotableCandidateFulfillmentGroupOffer> qualifiedFGOffers, PromotableOrder order);

}
