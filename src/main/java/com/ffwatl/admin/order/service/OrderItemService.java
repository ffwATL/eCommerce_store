package com.ffwatl.admin.order.service;

import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.service.call.OrderItemRequest;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.user.domain.Message;
import com.ffwatl.common.persistence.FetchMode;

/**
 * @author ffw_ATL.
 */
public interface OrderItemService {

    OrderItem findOrderItemById(long orderItemId, FetchMode fetchMode);

    OrderItem saveOrderItem(OrderItem orderItem);

    void delete(OrderItem item);

    Message createPersonalMessage();

    /**
     * Creates an OrderItemRequestDTO object that most closely resembles the given OrderItem.
     * That is, it will copy the SKU and quantity and attempt to copy the product and productCategory
     * if they exist.
     *
     * @param item the item to copy
     * @return the OrderItemRequestDTO that mirrors the item
     */
    OrderItemRequestDTO buildOrderItemRequestDTOFromOrderItem(OrderItem item);

    OrderItem createOrderItem(OrderItemRequest itemRequest);
}