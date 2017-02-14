package com.ffwatl.admin.order.domain;


import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.offer.domain.OrderItemPriceDetailAdjustment;

import java.io.Serializable;
import java.util.Set;

public interface OrderItemPriceDetail extends Serializable {

    long getId();

    /**
     * Reference back to the containing orderItem.
     */
    OrderItem getOrderItem();

    /**
     * Returns a List of the adjustments that effected this priceDetail.
     * @return a  List of OrderItemPriceDetailAdjustment
     */
    Set<OrderItemPriceDetailAdjustment> getOrderItemPriceDetailAdjustments();

    /**
     * The quantity of this {@link OrderItemPriceDetail}.
     * @return the quantity of this {@link OrderItemPriceDetail}.
     */
    int getQuantity();

    /**
     * Returns the value of all adjustments for a single quantity of the item.
     * Use {@link #getTotalAdjustmentValue()} to get the total for all quantities of this item.
     * @return the value of all adjustments for a single quantity of the item.
     */
    int getAdjustmentValue();

    /**
     * Returns getAdjustmentValue() * the quantity.
     * @return getAdjustmentValue() * the quantity.
     */
    int getTotalAdjustmentValue();

    /**
     * Returns the total adjustedPrice.
     * @return the total adjustedPrice.
     */
    int getTotalAdjustedPrice();


    Currency getCurrency();

    OrderItemPriceDetail setId(long id);

    OrderItemPriceDetail setOrderItem(OrderItem orderItem);

    OrderItemPriceDetail setOrderItemPriceDetailAdjustments(Set<OrderItemPriceDetailAdjustment> orderItemPriceDetailAdjustments);

    OrderItemPriceDetail setQuantity(int quantity);

}