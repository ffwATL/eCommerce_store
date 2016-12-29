package com.ffwatl.admin.offer.service;


import com.ffwatl.admin.catalog.domain.Category;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.common.rule.OfferRule;
import com.ffwatl.common.rule.Rule;

public class ItemCategoryOfferRule implements OfferRule<Category>{

    private long id;
    private Category bound;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean isMatch(Order target) {
        if(target == null) return false;

        return false;
    }

    @Override
    public Category getBound() {
        return this.bound;
    }

    @Override
    public Rule<Order, Category> setBound(Category bound) {
        this.bound = bound;
        return this;
    }

    @Override
    public Rule<Order, Category> setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "ItemCategoryOfferRule{" +
                "id=" + id +
                ", bound=" + bound +
                '}';
    }
}