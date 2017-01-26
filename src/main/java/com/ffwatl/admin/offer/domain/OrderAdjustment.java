package com.ffwatl.admin.offer.domain;


import com.ffwatl.admin.order.domain.Order;

import java.io.Serializable;

public interface OrderAdjustment extends Serializable{

    OrderAdjustment init(Order order, Offer offer, String reason);

    long getId();

    Offer getOffer();

    String getReason();

    int getAdjustmentValue();

    Order getOrder();

    OrderAdjustment setId(long id);

    OrderAdjustment setOffer(Offer offer);

    OrderAdjustment setAdjustmentValue(int adjustmentValue);

    OrderAdjustment setReason(String reason);

    OrderAdjustment setOrder(Order order);
}