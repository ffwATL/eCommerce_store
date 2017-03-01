package com.ffwatl.admin.order.dao;


import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderItemPriceDetail;
import com.ffwatl.admin.order.domain.OrderItemQualifier;
import com.ffwatl.admin.user.domain.Message;
import com.ffwatl.common.persistence.FetchMode;

public interface OrderItemDao {

    OrderItem findOrderItemById(long id, FetchMode fetchMode);

    OrderItem save(OrderItem orderItem);

    void delete(OrderItem orderItem);

    OrderItem create();

    Message createPersonalMessage();

    OrderItemPriceDetail createOrderItemPriceDetail();

    OrderItemQualifier createOrderItemQualifier();

    /**
     * Sets the initial orderItemPriceDetail for the item.
     */
    OrderItemPriceDetail initializeOrderItemPriceDetails(OrderItem item);
}