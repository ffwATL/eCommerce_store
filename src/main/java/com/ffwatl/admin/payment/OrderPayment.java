package com.ffwatl.admin.payment;

import com.ffwatl.admin.currency.Currency;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.user.domain.Address;

import java.io.Serializable;


public interface OrderPayment extends Serializable{

    long getId();

    Order getOrder();

    Address getBillingAddress();

    int getAmount();

    Currency getCurrency();

    String getReferenceNumber();

    PaymentType getType();




    OrderPayment setId(long id);

    OrderPayment setOrder(Order order);

    OrderPayment setBillingAddress(Address billingAddress);

    OrderPayment setAmount(int amount);

    OrderPayment setCurrency(Currency currency);

    OrderPayment setReferenceNumber(String referenceNumber);

    OrderPayment setType(PaymentType type);
}