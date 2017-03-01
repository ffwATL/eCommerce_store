package com.ffwatl.admin.payment.dao;


import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.payment.domain.OrderPayment;
import com.ffwatl.common.persistence.FetchMode;


import java.util.List;

public interface OrderPaymentDao {

    OrderPayment findPaymentById(long paymentId, FetchMode fetchMode);

    OrderPayment save(OrderPayment payment);

    List<OrderPayment> findPaymentsForOrder(Order order, FetchMode fetchMode);

    OrderPayment create();

    void delete(OrderPayment payment);

}