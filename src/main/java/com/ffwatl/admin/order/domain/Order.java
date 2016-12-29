package com.ffwatl.admin.order.domain;

import com.ffwatl.admin.currency.Currency;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.user.domain.User;

import java.util.Date;
import java.util.List;

/**
 * Created by ffw_ATL on 29-Dec-16.
 */
public interface Order {
    long getId();
    List<OrderItem> getOrderItems();
    User getCustomer();
    OrderStatus getOrderStatus();
    Date getSubmitDate();
    Currency getCurrency();
    Offer getOffer();


    void setId(long id);
    void setOrderItems(List<OrderItem> orderItems);
    void setCustomer(User customer);
    void setOrderStatus(OrderStatus orderStatus);
    void setSubmitDate(Date submitDate);
    void setCurrency(Currency currency);
    void setOffer(Offer offer);
}