package com.ffwatl.admin.order.domain;


import com.ffwatl.admin.currency.Currency;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OfferImpl;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.UserImpl;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderImpl implements Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private int totalFulfillmentCharges;
    private int subTotal;
    private int total;
    private Date submitDate;
    private OrderStatus orderStatus = OrderStatus.NEW;
    private long orderNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private FulfillmentGroup fulfillmentGroup;

    @OneToOne(cascade = CascadeType.PERSIST, targetEntity = UserImpl.class)
    private User customer;

    private Currency currency;

    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = OfferImpl.class)
    private Offer offer;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    @Override
    public User getCustomer() {
        return customer;
    }

    @Override
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    @Override
    public Date getSubmitDate() {
        return submitDate;
    }



    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public Offer getOffer() {
        return offer;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public void setCustomer(User customer) {
        this.customer = customer;
    }

    @Override
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }



    @Override
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public void setOffer(Offer offer) {
        this.offer = offer;
    }
}