package com.ffwatl.admin.order.service.call;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ffw_ATL.
 */
public class ReconstructCartResponseImpl implements ReconstructCartResponse {

    private Order order;
    private List<OrderItem> removedItems = new ArrayList<>();

    @Override
    public Order getOrder() {
        return order;
    }

    @Override
    public List<OrderItem> getRemovedItems() {
        return removedItems;
    }

    @Override
    public ReconstructCartResponse setOrder(Order order) {
        this.order = order;
        return this;
    }

    @Override
    public ReconstructCartResponse setRemovedItems(List<OrderItem> removedItems) {
        this.removedItems = removedItems;
        return this;
    }

    @Override
    public String toString() {
        return "ReconstructCartResponseImpl{" +
                "order=" + order +
                ", removedItems=" + removedItems +
                '}';
    }
}