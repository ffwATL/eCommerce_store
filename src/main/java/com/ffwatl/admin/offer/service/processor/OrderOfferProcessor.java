package com.ffwatl.admin.offer.service.processor;


import com.ffwatl.admin.offer.dao.OfferDao;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.service.discount.PromotableCandidateOrderOffer;
import com.ffwatl.admin.offer.service.discount.PromotableItemFactory;
import com.ffwatl.admin.offer.service.discount.PromotableOrder;
import com.ffwatl.admin.order.dao.OrderItemDao;

import java.util.List;
import java.util.Map;

public interface OrderOfferProcessor {

    void filterOrderLevelOffer(PromotableOrder promotableOrder, List<PromotableCandidateOrderOffer> qualifiedOrderOffers, Offer offer);

    Boolean executeExpression(String expression, Map<String, Object> vars);

    /**
     * Executes the appliesToOrderRules in the Offer to determine if this offer
     * can be applied to the Order, OrderItem, or FulfillmentGroup.
     *
     * @param offer
     * @param promotableOrder
     * @return true if offer can be applied, otherwise false
     */
    boolean couldOfferApplyToOrder(Offer offer, PromotableOrder promotableOrder);

    List<PromotableCandidateOrderOffer> removeTrailingNotCombinableOrderOffers(List<PromotableCandidateOrderOffer> candidateOffers);

    /**
     * Takes a list of sorted CandidateOrderOffers and determines if each offer can be
     * applied based on the restrictions (stackable and/or combinable) on that offer.  OrderAdjustments
     * are create on the Order for each applied CandidateOrderOffer.  An offer with stackable equals false
     * cannot be applied to an Order that already contains an OrderAdjustment.  An offer with combinable
     * equals false cannot be applied to the Order if the Order already contains an OrderAdjustment.
     *
     * @param orderOffers a sorted list of CandidateOrderOffer
     * @param promotableOrder the Order to apply the CandidateOrderOffers
     */
    void applyAllOrderOffers(List<PromotableCandidateOrderOffer> orderOffers, PromotableOrder promotableOrder);

    PromotableItemFactory getPromotableItemFactory();

    void setPromotableItemFactory(PromotableItemFactory promotableItemFactory);

    /**
     * Takes the adjustments and PriceDetails from the passed in PromotableOrder and transfers them to the
     * actual order first checking to see if they already exist.
     *
     * @param promotableOrder
     */
    void synchronizeAdjustmentsAndPrices(PromotableOrder promotableOrder);

    /**
     * Set the offerDao (primarily for unit testing)
     */
    void setOfferDao(OfferDao offerDao);

    /**
     * Set the orderItemDao (primarily for unit testing)
     */
    void setOrderItemDao(OrderItemDao orderItemDao);
}