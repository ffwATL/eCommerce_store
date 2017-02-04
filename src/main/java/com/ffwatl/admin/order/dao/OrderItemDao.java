package com.ffwatl.admin.order.dao;


import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderItemPriceDetail;
import com.ffwatl.admin.order.domain.OrderItemQualifier;
import com.ffwatl.common.FetchMode;

public interface OrderItemDao {

    OrderItem findOrderItemById(FetchMode fetchMode, long id);

    OrderItem save(OrderItem orderItem);

    void delete(OrderItem orderItem);

    OrderItem create();

    /*PersonalMessage createPersonalMessage();*/

    OrderItemPriceDetail createOrderItemPriceDetail();

    OrderItemQualifier createOrderItemQualifier();

    /**
     * Sets the initial orderItemPriceDetail for the item.
     */
    OrderItemPriceDetail initializeOrderItemPriceDetails(OrderItem item);
}