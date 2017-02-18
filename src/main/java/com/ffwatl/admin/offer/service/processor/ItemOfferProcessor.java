package com.ffwatl.admin.offer.service.processor;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.service.discount.PromotableCandidateItemOffer;
import com.ffwatl.admin.offer.service.discount.PromotableCandidateOrderOffer;
import com.ffwatl.admin.offer.service.discount.PromotableOrder;

import java.util.List;

public interface ItemOfferProcessor {

    /**
     * Review an item level offer against the list of discountable items from the order. If the
     * offer applies, add it to the qualifiedItemOffers list.
     *
     * @param order the BLC order
     * @param qualifiedItemOffers the container list for any qualified offers
     * @param offer the offer in question
     */
    void filterItemLevelOffer(PromotableOrder order, List<PromotableCandidateItemOffer> qualifiedItemOffers,
                              Offer offer);

    /**
     * Private method that takes a list of sorted CandidateItemOffers and determines if each offer can be
     * applied based on the restrictions (stackable and/or combinable) on that offer.  OrderItemAdjustments
     * are create on the OrderItem for each applied CandidateItemOffer.  An offer with stackable equals false
     * cannot be applied to an OrderItem that already contains an OrderItemAdjustment.  An offer with combinable
     * equals false cannot be applied to an OrderItem if that OrderItem already contains an
     * OrderItemAdjustment, unless the offer is the same offer as the OrderItemAdjustment offer.
     *
     * @param itemOffers a sorted list of CandidateItemOffer
     */
    void applyAllItemOffers(List<PromotableCandidateItemOffer> itemOffers, PromotableOrder order);

    void applyAndCompareOrderAndItemOffers(PromotableOrder order, List<PromotableCandidateOrderOffer> qualifiedOrderOffers, List<PromotableCandidateItemOffer> qualifiedItemOffers);

    void filterOffers(PromotableOrder order, List<Offer> filteredOffers,
                      List<PromotableCandidateOrderOffer> qualifiedOrderOffers,
                      List<PromotableCandidateItemOffer> qualifiedItemOffers);
}