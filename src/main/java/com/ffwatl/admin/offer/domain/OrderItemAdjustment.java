package com.ffwatl.admin.offer.domain;


import com.ffwatl.admin.order.domain.OrderItem;

import java.io.Serializable;

public interface OrderItemAdjustment extends Serializable{

    void init(OrderItem orderItem, Offer offer, String reason);

    long getId();

    OrderItem getOrderItem();

    Offer getOffer();

    String getReason();

    int getAdjustmentValue();

    boolean isAppliedToSalePrice();

    int getRetailPriceValue();

    int getSalesPriceValue();


    OrderItemAdjustment setId(long id);

    OrderItemAdjustment setOrderItem(OrderItem orderItem);

    OrderItemAdjustment setOffer(Offer offer);

    OrderItemAdjustment setReason(String reason);

    OrderItemAdjustment setAdjustmentValue(int value);

    OrderItemAdjustment setAppliedToSalePrice(boolean appliedToSalePrice);

    OrderItemAdjustment setRetailPriceValue(int retailPriceValue);

    OrderItemAdjustment setSalesPriceValue(int salesPriceValue);
}