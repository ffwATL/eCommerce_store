package com.ffwatl.admin.order.service.call;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ffw_ATL.
 */
public class MergeCartResponseImpl implements MergeCartResponse {

    private static final long serialVersionUID = 1L;

    private Order order;
    private List<OrderItem> addedItems = new ArrayList<>();
    private List<OrderItem> removedItems = new ArrayList<>();
    private boolean merged;

    @Override
    public Order getOrder() {
        return order;
    }

    @Override
    public List<OrderItem> getAddedItems() {
        return addedItems;
    }

    @Override
    public List<OrderItem> getRemovedItems() {
        return removedItems;
    }

    @Override
    public boolean isMerged() {
        return merged;
    }

    @Override
    public MergeCartResponse setOrder(Order order) {
        this.order = order;
        return this;
    }

    @Override
    public MergeCartResponse setAddedItems(List<OrderItem> addedItems) {
        this.addedItems = addedItems;
        return this;
    }

    @Override
    public MergeCartResponse setRemovedItems(List<OrderItem> removedItems) {
        this.removedItems = removedItems;
        return this;
    }

    @Override
    public MergeCartResponse setMerged(boolean merged) {
        this.merged = merged;
        return this;
    }

    @Override
    public String toString() {
        return "MergeCartResponseImpl{" +
                "order=" + order +
                ", addedItems=" + addedItems +
                ", removedItems=" + removedItems +
                ", merged=" + merged +
                '}';
    }
}