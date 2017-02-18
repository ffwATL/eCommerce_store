package com.ffwatl.admin.order.dao;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderLock;
import com.ffwatl.admin.order.domain.OrderStatus;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.FetchMode;

import java.util.List;



public interface OrderDao {

    Order findOrderById(long id, FetchMode fetchMode);

    Order findOrderById(long id, boolean refresh);

    List<Order> findOrdersByIds(FetchMode fetchMode, long... ids);

    /**
     * Reads a batch list of orders from the DB.  The status is optional and can be null.  If no status
     * is provided, then all order will be read.  Otherwise, only orders with that status will be read.
     * @param start
     * @param pageSize
     * @param statuses
     * @return
     */
    List<Order> findBatchOrders(int start, int pageSize, List<OrderStatus> statuses, FetchMode fetchMode);

    List<Order> findOrdersForCustomer(long id, OrderStatus orderStatus, FetchMode fetchMode);

    List<Order> findOrdersForCustomer(long id, FetchMode fetchMode);

    Order findNamedOrderForCustomer(long customerId, String name, FetchMode fetchMode);

    Order findCartForCustomer(long customerId, FetchMode fetchMode);

    Order save(Order order);

    void delete(Order order);

    Order submitOrder(Order cartOrder);

    Order create();

    Order createNewCartForCustomer(User customer, Currency currency);

    Order findOrderByOrderNumber(String orderNumber, FetchMode fetchMode);


    /**
     * This method will attempt to update the {@link OrderLock} object table for the given order to mark it as
     * locked, provided the OrderLock record for the given order was not already locked. It will return true or
     * false depending on whether or not the lock was able to be acquired.
     *
     * @param order
     * @return true if the lock was acquired, false otherwise
     */
    boolean acquireLock(Order order);

    /**
     * Releases the lock for the given order. Note that this method will release the lock for the order whether or not
     * the caller was the current owner of the lock. As such, callers of this method should take care to ensure they
     * hold the lock before attempting to release it.
     *
     * @param order
     * @return true if the lock was successfully released, false otherwise
     */
    boolean releaseLock(Order order);
}
