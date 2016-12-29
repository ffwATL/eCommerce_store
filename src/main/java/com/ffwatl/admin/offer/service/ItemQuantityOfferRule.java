package com.ffwatl.admin.offer.service;


import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.common.rule.OfferRule;
import com.ffwatl.common.rule.Rule;

import java.util.List;

public class ItemQuantityOfferRule implements OfferRule<Integer> {

    private long id;
    private int bound;


    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean isMatch(Order order){
        if(order == null) return false;
        List<OrderItem> orderItems = order.getOrderItems();
        return orderItems != null && orderItems.size() >= bound;
    }

    @Override
    public Integer getBound() {
        return bound;
    }

    @Override
    public Rule<Order, Integer> setBound(Integer bound) {
        this.bound = bound;
        return this;
    }

    @Override
    public Rule<Order, Integer> setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "ItemQuantityOfferRule{" +
                "bound=" + bound +
                '}';
    }
}