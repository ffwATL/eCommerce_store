package com.ffwatl.admin.order.service;

import com.ffwatl.admin.order.dao.OrderItemDao;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.service.call.OrderItemRequest;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.user.domain.Message;
import com.ffwatl.common.persistence.FetchMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
@Service("order_item_service")
@Transactional(readOnly = true)
public class OrderItemServiceImpl implements OrderItemService {

    @Resource(name = "order_item_dao")
    private OrderItemDao orderItemDao;


    @Override
    public OrderItem findOrderItemById(long orderItemId, FetchMode fetchMode) {
        return orderItemDao.findOrderItemById(orderItemId, fetchMode);
    }

    @Override
    @Transactional
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemDao.save(orderItem);
    }

    @Override
    @Transactional
    public void delete(OrderItem item) {
        orderItemDao.delete(item);
    }

    @Override
    public Message createPersonalMessage() {
        return orderItemDao.createPersonalMessage();
    }

    @Override
    public OrderItemRequestDTO buildOrderItemRequestDTOFromOrderItem(OrderItem item) {
        return new OrderItemRequestDTO()
                .setProductName(item.getProductName())
                .setQuantity(item.getQuantity())
                .setOverrideRetailPrice(item.getRetailPrice())
                .setOverrideSalePrice(item.getSalePrice())
                .setItemAttribute(item.getProductAttribute());
    }

    @Override
    public OrderItem createOrderItem(OrderItemRequest itemRequest) {
        return orderItemDao.create()
                .setProductName(itemRequest.getItemName())
                .setQuantity(itemRequest.getQuantity())
                .setOrder(itemRequest.getOrder())
                .setSalePrice(itemRequest.getSalePriceOverride())
                .setRetailPrice(itemRequest.getRetailPriceOverride())
                .setProductAttribute(itemRequest.getProductAttribute())
                .setCategory(itemRequest.getCategory());
    }
}