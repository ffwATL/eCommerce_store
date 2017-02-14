package com.ffwatl.admin.offer.service.discount;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.order.domain.OrderItem;

import java.io.Serializable;
import java.util.List;
import java.util.Map;



public interface PromotableOrderItem extends Serializable {

    /**
     * Adds the item to the rule variables map.
     */
    void updateRuleVariables(Map<String, Object> ruleVars);

    /**
     * Called by pricing engine to reset the state of this item.
     */
    void resetPriceDetails();

    /**
     * Returns true if this item can receive item level discounts.
     * @return true if this item can receive item level discounts.
     */
    boolean isDiscountingAllowed();

    /**
     * Returns the salePrice without adjustments
     */
    int getSalePriceBeforeAdjustments();

    /**
     * Returns the retailPrice without adjustments
     */
    int getRetailPriceBeforeAdjustments();

    /**
     * Returns true if the item has a sale price that is lower than the retail price.
     */
    boolean isOnSale();

    /**
     * Returns the list of priceDetails associated with this item.
     * @return the list of priceDetails associated with this item.
     */
    List<PromotableOrderItemPriceDetail> getPromotableOrderItemPriceDetails();

    /**
     * Return the salePriceBeforeAdjustments if the passed in param is true.
     * Otherwise return the retailPriceBeforeAdjustments.
     * @return the salePriceBeforeAdjustments if the passed in param is true;
     */
    int getPriceBeforeAdjustments(boolean applyToSalePrice);

    /**
     * @return the basePrice of the item (baseSalePrice or baseRetailPrice)
     */
    int getCurrentBasePrice();

    /**
     * Returns the quantity for this orderItem
     * @return the quantity for this orderItem
     */
    int getQuantity();

    /**
     * Returns the currency of the related order.
     * @return the currency of the related order.
     */
    Currency getCurrency();

    /**
     * Effectively deletes all priceDetails associated with this item and r
     */
    void removeAllItemAdjustments();

    /**
     * Merges any priceDetails that share the same adjustments.
     */
    void mergeLikeDetails();

    /**
     * Returns the id of the contained OrderItem
     */
    long getOrderItemId();

    /**
     * Returns the value of all adjustments.
     */
    int calculateTotalAdjustmentValue();

    /**
     * Returns the final total for this item taking into account the finalized
     * adjustments. Intended to be called after the adjustments have been finalized.
     */
    int calculateTotalWithAdjustments();

    /**
     * Returns the total for this item if not adjustments applied.
     */
    int calculateTotalWithoutAdjustments();

    /**
     * Creates a new detail with the associated quantity. Intended for use as part of the PriceDetail split.
     * @return a new detail with the associated quantity.
     */
    PromotableOrderItemPriceDetail createNewDetail(int quantity);

    /**
     * Returns the underlying orderItem.    Manipulation of the underlying orderItem is not recommended.
     * This method is intended for unit test and read only access although that is not strictly enforced.
     * @return the underlying orderItem.
     */
    OrderItem getOrderItem();

    /**
     * Map available to implementations to store data needed for custom logic.
     */
    Map<String, Object> getExtraDataMap();
}