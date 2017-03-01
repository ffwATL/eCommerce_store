package com.ffwatl.admin.payment.domain;


import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderImpl;
import com.ffwatl.admin.payment.PaymentType;
import com.ffwatl.admin.user.domain.Address;
import com.ffwatl.admin.user.domain.AddressImpl;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.UserImpl;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_payments")
public class OrderPaymentImpl implements OrderPayment {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = OrderImpl.class)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(targetEntity = AddressImpl.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "address_id")
    private Address billingAddress;

    @Column(name = "reference_number")
    private String referenceNumber;

    @Column(name = "payment_type")
    private String type = "";

    @Column(name = "amount")
    private int amount;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne(targetEntity = UserImpl.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "customer_id")
    private User customer;


    @Override
    public long getId() {
        return id;
    }

    @Override
    public Order getOrder() {
        return order;
    }

    @Override
    public Address getBillingAddress() {
        return billingAddress;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String getReferenceNumber() {
        return referenceNumber;
    }

    @Override
    public PaymentType getType() {
        return PaymentType.getInstance(type);
    }

    @Override
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public User getCustomer() {
        return customer;
    }

    @Override
    public OrderPayment setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public OrderPayment setOrder(Order order) {
        this.order = order;
        return this;
    }

    @Override
    public OrderPayment setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    @Override
    public OrderPayment setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public OrderPayment setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public OrderPayment setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    @Override
    public OrderPayment setType(PaymentType type) {
        if(type != null) this.type = type.getType();
        return this;
    }

    @Override
    public OrderPayment setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    @Override
    public OrderPayment setCustomer(User customer) {
        this.customer = customer;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderPaymentImpl that = (OrderPaymentImpl) o;

        if (getId() != that.getId()) return false;
        if (getAmount() != that.getAmount()) return false;
        if (getOrder() != null ? !getOrder().equals(that.getOrder()) : that.getOrder() != null) return false;
        if (getBillingAddress() != null ? !getBillingAddress().equals(that.getBillingAddress()) : that.getBillingAddress() != null)
            return false;
        if (getReferenceNumber() != null ? !getReferenceNumber().equals(that.getReferenceNumber()) : that.getReferenceNumber() != null)
            return false;
        if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null) return false;
        if(getDateTime() != null ? !getDateTime().equals(that.getDateTime()) : that.getDateTime() != null) return false;
        if(getCustomer() != null ? !getCustomer().equals(that.getCustomer()) : that.getCustomer() != null) return false;
        return getCurrency() == that.getCurrency();

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getOrder() != null ? getOrder().hashCode() : 0);
        result = 31 * result + (getBillingAddress() != null ? getBillingAddress().hashCode() : 0);
        result = 31 * result + (getReferenceNumber() != null ? getReferenceNumber().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + getAmount();
        result = 31 * result + (getCurrency() != null ? getCurrency().hashCode() : 0);
        result = 31 * result + (getDateTime() != null ? getDateTime().hashCode() : 0);
        result = 31 * result + (getCustomer() != null ? getCustomer().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderPaymentImpl{" +
                "id=" + id +
                ", order=" + order +
                ", billingAddress=" + billingAddress +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", currency=" + currency +
                ", dateTime=" + dateTime +
                ", customer=" + customer +
                '}';
    }
}