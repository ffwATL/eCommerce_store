package com.ffwatl.admin.order.service.call;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;

import java.io.Serializable;
import java.util.List;

/**
 * @author ffw_ATL.
 */
public interface MergeCartResponse extends Serializable {
    Order getOrder();

    List<OrderItem> getAddedItems();

    List<OrderItem> getRemovedItems();

    boolean isMerged();

    MergeCartResponse setOrder(Order order);

    MergeCartResponse setAddedItems(List<OrderItem> addedItems);

    MergeCartResponse setRemovedItems(List<OrderItem> removedItems);

    MergeCartResponse setMerged(boolean merged);

    @Override
    String toString();
}
