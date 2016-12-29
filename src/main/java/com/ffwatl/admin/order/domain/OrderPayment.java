package com.ffwatl.admin.order.domain;

import com.ffwatl.admin.currency.Currency;
import com.ffwatl.admin.user.domain.Address;



public interface OrderPayment {

    long getId();
    Order getOrder();
    Address getBillingAddress();
    int getAmount();
    Currency getCurrency();

    OrderPayment setId(long id);
    OrderPayment setOrder(Order order);
    OrderPayment setBillingAddress(Address billingAddress);
    OrderPayment setAmount(int amount);
    OrderPayment setCurrency(Currency currency);
}