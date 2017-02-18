package com.ffwatl.admin.offer.service;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OrderItemPriceDetailAdjustment;
import com.ffwatl.admin.offer.service.discount.PromotableCandidateItemOffer;
import com.ffwatl.admin.offer.service.discount.PromotableOrder;
import com.ffwatl.admin.offer.service.discount.PromotableOrderItem;
import com.ffwatl.admin.offer.service.discount.PromotableOrderItemPriceDetail;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderItemPriceDetail;
import com.ffwatl.admin.order.domain.OrderItemQualifier;
import com.ffwatl.common.rule.Rule;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class serves to allow reuse of logic between the core offer service and related
 * offer service extensions. Changes here likely affect other modules including advanced
 * offer and subscription.
 */
public interface OfferServiceUtilities {

    /**
     * Used in {@link ItemOfferProcessorImpl#applyItemQualifiersAndTargets(PromotableCandidateItemOffer, PromotableOrder)}
     * Allow for customized sorting for which qualifier items should be attempted to be used first for a promotion.
     *
     * Default behavior is to sort descending, so higher-value items are attempted to be discounted first.
     */
    void sortTargetItemDetails(List<PromotableOrderItemPriceDetail> itemPriceDetails, boolean applyToSalePrice);

    /**
     * Used in {@link ItemOfferProcessorImpl#applyItemQualifiersAndTargets(PromotableCandidateItemOffer, PromotableOrder)}
     * Allow for customized sorting for which qualifier items should be attempted to be used first for a promotion.
     *
     * Default behavior is to sort descending, so higher-value items are attempted to be qualified first.
     */
    void sortQualifierItemDetails(List<PromotableOrderItemPriceDetail> itemPriceDetails, boolean applyToSalePrice);

    /**
     * Given an orderItem, finds the top most parent order item.
     */
    OrderItem findRelatedQualifierRoot(OrderItem relatedQualifier);

    /**
     * Return false if a totalitarian or non-combinable offer has already been applied or if this offer is
     * totalitarian or non-combinable  and this order already has adjustments applied.
     */
    boolean itemOfferCanBeApplied(PromotableCandidateItemOffer itemOffer,
                                         List<PromotableOrderItemPriceDetail> details);

    /**
     * Returns the number of qualifiers marked for the passed in rule
     */
    int markQualifiersForCriteria(PromotableCandidateItemOffer itemOffer, Rule rule,
                                  List<PromotableOrderItemPriceDetail> priceDetails);

    /**
     * Returns the number of targets marked for the passed in rule
     */
    int markTargetsForCriteria(PromotableCandidateItemOffer itemOffer, OrderItem relatedQualifier, boolean checkOnly,
                               Offer promotion, OrderItem relatedQualifierRoot, Rule rule,
                               List<PromotableOrderItemPriceDetail> priceDetails, int targetQtyNeeded);

    /**
     * Returns the number of targets marked for the passed in Rule
     */
    int markRelatedQualifiersAndTargetsForItemCriteria(PromotableCandidateItemOffer itemOffer, PromotableOrder order,
                                                       OrderItem orderItem, Rule rule,
                                                       List<PromotableOrderItemPriceDetail> priceDetails);

    /**
     * Takes in a list of {@link PromotableOrderItemPriceDetail}s  and applies adjustments for all of the
     * discounts that match the passed in offer.
     */
    void applyAdjustmentsForItemPriceDetails(PromotableCandidateItemOffer itemOffer,
                                             List<PromotableOrderItemPriceDetail> itemPriceDetails);

    /**
     * Used by applyAdjustments to create an OrderItemAdjustment from a CandidateOrderOffer
     * and associates the OrderItemAdjustment to the OrderItem.
     *
     * @param itemOffer a CandidateItemOffer to apply to an Item
     */
    void applyOrderItemAdjustment(PromotableCandidateItemOffer itemOffer, PromotableOrderItemPriceDetail itemPriceDetail);

    /**
     * Builds the list of order-items at the level they are being priced which includes splitting bundles that are
     * being priced at the item level.
     */
    List<OrderItem> buildOrderItemList(Order order);

    /**
     * Builds a map from orderItem to promotableOrderItem.
     */
    Map<OrderItem, PromotableOrderItem> buildPromotableItemMap(PromotableOrder promotableOrder);

    /**
     * Builds a map from itemDetails for adjustment processing.
     */
    Map<Long, OrderItemPriceDetailAdjustment> buildItemDetailAdjustmentMap(OrderItemPriceDetail itemDetail);


    /**
     * Updates the passed in price detail and its associated adjustments.
     */
    void updatePriceDetail(OrderItemPriceDetail itemDetail, PromotableOrderItemPriceDetail promotableDetail);

    /**
     * Removes price details from the iterator that are contained in the passed in map.
     */
    void removeUnmatchedPriceDetails(Map<Long, ? extends OrderItemPriceDetail> unmatchedDetailsMap,
                                     Iterator<? extends OrderItemPriceDetail> pdIterator);

    /**
     * Removes qualifiers from the iterator that are contained in the passed in map.
     */
    void removeUnmatchedQualifiers(Map<Long, ? extends OrderItemQualifier> unmatchedQualifiersMap,
                                   Iterator<? extends OrderItemQualifier> qIterator);
}