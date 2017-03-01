package com.ffwatl.admin.order.service.call;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;

import java.util.List;

/**
 * @author ffw_ATL.
 */
public interface ReconstructCartResponse {
    Order getOrder();

    List<OrderItem> getRemovedItems();

    ReconstructCartResponse setOrder(Order order);

    ReconstructCartResponse setRemovedItems(List<OrderItem> removedItems);
}
