package com.ffwatl.admin.order.service.workflow;

import com.ffwatl.admin.order.domain.FulfillmentGroupItem;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ffw_ATL.
 */
public class CartOperationRequest {

    protected OrderItemRequestDTO itemRequest;

    protected Order order;

    protected boolean priceOrder;

    // Set during the course of the workflow for use in subsequent workflow steps
    protected OrderItem orderItem;

    // Set during the course of the workflow for use in subsequent workflow steps
    protected Integer orderItemQuantityDelta;

    protected List<Long[]> multishipOptionsToDelete = new ArrayList<>();
    protected List<FulfillmentGroupItem> fgisToDelete = new ArrayList<>();
    protected List<OrderItem> oisToDelete = new ArrayList<>();

    public CartOperationRequest(Order order, OrderItemRequestDTO itemRequest, boolean priceOrder) {
        setOrder(order);
        setItemRequest(itemRequest);
        setPriceOrder(priceOrder);
    }

    public OrderItemRequestDTO getItemRequest() {
        return itemRequest;
    }

    public void setItemRequest(OrderItemRequestDTO itemRequest) {
        this.itemRequest = itemRequest;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean isPriceOrder() {
        return priceOrder;
    }

    public void setPriceOrder(boolean priceOrder) {
        this.priceOrder = priceOrder;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    /**
     * @deprecated in favor of {@link #getOrderItem()}. Keeping this method for backwards compatibility
     */
    public OrderItem getAddedOrderItem() {
        return orderItem;
    }

    /**
     * @deprecated in favor of {@link #setOrderItem(OrderItem)}. Keeping this method for backwards compatibility
     */
    public void setAddedOrderItem(OrderItem addedOrderItem) {
        this.orderItem = addedOrderItem;
    }

    public Integer getOrderItemQuantityDelta() {
        return orderItemQuantityDelta;
    }

    public void setOrderItemQuantityDelta(Integer orderItemQuantityDelta) {
        this.orderItemQuantityDelta = orderItemQuantityDelta;
    }

    public List<Long[]> getMultishipOptionsToDelete() {
        return multishipOptionsToDelete;
    }

    public void setMultishipOptionsToDelete(List<Long[]> multishipOptionsToDelete) {
        this.multishipOptionsToDelete = multishipOptionsToDelete;
    }

    public List<FulfillmentGroupItem> getFgisToDelete() {
        return fgisToDelete;
    }

    public void setFgisToDelete(List<FulfillmentGroupItem> fgisToDelete) {
        this.fgisToDelete = fgisToDelete;
    }

    public List<OrderItem> getOisToDelete() {
        return oisToDelete;
    }

    public void setOisToDelete(List<OrderItem> oisToDelete) {
        this.oisToDelete = oisToDelete;
    }
}
