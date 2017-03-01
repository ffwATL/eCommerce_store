package com.ffwatl.admin.order.service.call;

import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;

/**
 * @author ffw_ATL.
 */
public class FulfillmentGroupItemRequest {

    private Order order;
    private FulfillmentGroup fulfillmentGroup;
    private OrderItem orderItem;
    private int quantity;

    public Order getOrder() {
        return order;
    }

    public FulfillmentGroup getFulfillmentGroup() {
        return fulfillmentGroup;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public FulfillmentGroupItemRequest setOrder(Order order) {
        this.order = order;
        return this;
    }

    public FulfillmentGroupItemRequest setFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
        this.fulfillmentGroup = fulfillmentGroup;
        return this;
    }

    public FulfillmentGroupItemRequest setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        return this;
    }

    public FulfillmentGroupItemRequest setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public String toString() {
        return "FulfillmentGroupItemRequest{" +
                "order=" + order +
                ", fulfillmentGroup=" + fulfillmentGroup +
                ", orderItem=" + orderItem +
                ", quantity=" + quantity +
                '}';
    }
}