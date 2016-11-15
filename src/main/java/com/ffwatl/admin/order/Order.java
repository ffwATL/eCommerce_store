package com.ffwatl.admin.order;


import com.ffwatl.admin.currency.Currency;
import com.ffwatl.admin.offer.PromoCode;
import com.ffwatl.admin.offer.PromoCodeImpl;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.UserImpl;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<OrderItem> items;

    @OneToOne(cascade = CascadeType.PERSIST, targetEntity = UserImpl.class)
    private User createdBy;

    private OrderState orderState;

    private Date date;

    private int originalPrice;

    private int salePrice;

    private int finalPrice;

    private int earn;

    private Currency currency;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = PromoCodeImpl.class)
    private PromoCode promoCode;

    public long getId() {
        return id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public Date getDate() {
        return date;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public int getFinalPrice() {
        return finalPrice;
    }

    public int getEarn() {
        return earn;
    }

    public Currency getCurrency() {
        return currency;
    }

    public PromoCode getPromoCode() {
        return promoCode;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public void setFinalPrice(int finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setEarn(int earn) {
        this.earn = earn;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setPromoCode(PromoCode promoCode) {
        this.promoCode = promoCode;
    }
}