package com.ffwatl.admin.offer.domain;


import com.ffwatl.admin.order.domain.Order;

public interface OrderAdjustment extends Adjustment {

    Order getOrder();

    void init(Order order, Offer offer, String reason);

    void setOrder(Order order);
}