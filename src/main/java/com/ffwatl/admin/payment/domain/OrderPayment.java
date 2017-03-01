package com.ffwatl.admin.payment.domain;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.payment.PaymentType;
import com.ffwatl.admin.user.domain.Address;
import com.ffwatl.admin.user.domain.User;

import java.io.Serializable;
import java.time.LocalDateTime;


public interface OrderPayment extends Serializable{

    long getId();

    Order getOrder();

    Address getBillingAddress();

    int getAmount();

    Currency getCurrency();

    String getReferenceNumber();

    PaymentType getType();


    LocalDateTime getDateTime();

    User getCustomer();

    OrderPayment setId(long id);

    OrderPayment setOrder(Order order);

    OrderPayment setBillingAddress(Address billingAddress);

    OrderPayment setAmount(int amount);

    OrderPayment setCurrency(Currency currency);

    OrderPayment setReferenceNumber(String referenceNumber);

    OrderPayment setType(PaymentType type);

    OrderPayment setDateTime(LocalDateTime dateTime);

    OrderPayment setCustomer(User customer);
}