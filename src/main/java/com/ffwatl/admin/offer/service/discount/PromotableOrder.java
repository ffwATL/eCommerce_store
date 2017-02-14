package com.ffwatl.admin.offer.service.discount;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.order.domain.Order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface PromotableOrder extends Serializable {

    /**
     * Sets the order subTotal to the sum of item total prices without
     * adjustments.
     */
    void setOrderSubTotalToPriceWithoutAdjustments();

    /**
     * Sets the order subTotal to the sum of item total prices without
     * adjustments.
     */
    void setOrderSubTotalToPriceWithAdjustments();

    /**
     * Returns all OrderItems for the order wrapped with PromotableOrderItem
     * @return all OrderItems for the order wrapped with PromotableOrderItem;
     */
    List<PromotableOrderItem> getAllOrderItems();

    /**
     * Returns all OrderItems that can receive discounts.  Sorts the results by SalePrice or RetailPrice
     * depending upon the passed in variable.
     * @param sortBySalePrice if true then sorts the results by SalePrice, otherwise - by RetailPrice;
     * @return all OrderItems that can receive discounts.
     */
    List<PromotableOrderItem> getDiscountableOrderItems(boolean sortBySalePrice);

    /**
     * Returns all OrderItems that can receive discounts.
     * @return all OrderItems that can receive discounts.
     */
    List<PromotableOrderItem> getDiscountableOrderItems();

    /**
     * Returns the fulfillmentGroups associated with the order after converting them to
     * promotableFulfillmentGroups.
     * @return the fulfillmentGroups associated with the order;
     */
    List<PromotableFulfillmentGroup> getFulfillmentGroups();

    /**
     * Returns true if this promotableOrder has any order adjustments.
     * @return true if this promotableOrder has any order adjustments.
     */
    boolean isHasOrderAdjustments();

    /**
     * Returns the list of orderAdjustments being proposed for the order.
     * This will be converted to actual order adjustments on completion of the offer processing.
     * @return the list of orderAdjustments being proposed for the order.
     */
    List<PromotableOrderAdjustment> getCandidateOrderAdjustments();

    /**
     * Adds the adjustment to the order's adjustment list and discounts the
     * order's adjustment price by the value of the adjustment.
     * @param orderAdjustment order adjustment
     */
    void addCandidateOrderAdjustment(PromotableOrderAdjustment orderAdjustment);

    /**
     * Removes all order, order item, and fulfillment adjustments from the order
     * and resets the adjustment price.
     */
    void removeAllCandidateOfferAdjustments();

    /**
     * Removes all order adjustments from the order and resets the adjustment
     * price.
     */
    void removeAllCandidateOrderOfferAdjustments();

    /**
     * Removes all adjustments from the order's order items and resets the
     * adjustment price for each item.
     */
    void removeAllCandidateItemOfferAdjustments();

    /**
     * Removes all adjustments from the order's fulfillment items and resets the
     * adjustment price for each item.
     */
    void removeAllCandidateFulfillmentOfferAdjustments();

    /**
     * Adds the underlying order to the rule variable map.
     */
    void updateRuleVariables(Map<String, Object> ruleVars);

    /**
     * Returns the associated order.
     */
    Order getOrder();

    /**
     * Returns true if a totalitarian offer has been applied.   A totalitarian offer is
     * an offer that does not allow any other offers to be used at the same time.   As
     * opposed to a "non-combinable" offer which can't be used with other offers of the
     * same type but can be used with other offers of a different type (e.g. a non-combinable order offer
     * can be used with a non-combinable item offer).
     * @return true if a totalitarian offer has been applied.
     */
    boolean isTotalitarianOfferApplied();

    /**
     * Calculates the total adjustment to be received from the order adjustments.
     * @return the total adjustment to be received from the order adjustments.
     */
    int calculateOrderAdjustmentTotal();

    /**
     * Calculates the total adjustment to be received from the item adjustments.
     * @return the total adjustment to be received from the item adjustments.
     */
    int calculateItemAdjustmentTotal();

    /**
     * Returns all of the price detail items for this order.
     * @return all of the price detail items for this order.
     */
    List<PromotableOrderItemPriceDetail> getAllPromotableOrderItemPriceDetails();

    /**
     * Returns true if this order can apply another order promotion.
     * Returns false if a totalitarian or not-combinable offer has already been applied
     * Returns false if the passed in order is not-combinable or totalitarian and this order already has adjustments
     */
    boolean canApplyOrderOffer(PromotableCandidateOrderOffer offer);

    /**
     * Returns the {@link Currency} for the current order.
     * @return the {@link Currency} for the current order.
     */
    Currency getOrderCurrency();

    /**
     * Sets the total fulfillmentCharges the order.
     * @param totalFulfillmentCharges the total fulfillmentCharges the order.
     */
    void setTotalFufillmentCharges(int totalFulfillmentCharges);

    /**
     * Returns the price of the order without adjustments.
     * @return the price of the order without adjustments.
     */
    int calculateSubtotalWithoutAdjustments();

    /**
     * Returns the price of the order with adjustments.
     * @return the price of the order with adjustments.
     */
    int calculateSubtotalWithAdjustments();

    /**
     * @return true if this order was created in a way that existing order and item adjustments
     * were copied over to this item.
     */
    boolean isIncludeOrderAndItemAdjustments();

    boolean isTotalitarianOrderOfferApplied();

    boolean isTotalitarianItemOfferApplied();

    boolean isTotalitarianFgOfferApplied();

    Map<String, Object> getExtraDataMap();
}