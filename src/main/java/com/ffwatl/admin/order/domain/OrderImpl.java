package com.ffwatl.admin.order.domain;




import com.ffwatl.admin.currency.Currency;
import com.ffwatl.admin.offer.domain.CandidateOrderOffer;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OfferCode;
import com.ffwatl.admin.offer.domain.OrderAdjustment;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.UserImpl;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "orders")
public class OrderImpl implements Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "order_name")
    private String name;

    @Column(name = "order_sub_total_value")
    private int subTotal;

    @Column(name = "order_total_value")
    private int total;

    @ManyToOne(targetEntity = UserImpl.class, optional = false)
    @JoinColumn(name = "customer_id")
    private User customer;

    @Column(name = "order_status")
    private OrderStatus orderStatus = OrderStatus.NEW;

    @OneToMany(fetch = FetchType.LAZY)
    @Column(name = "order_items")
    private List<OrderItem> orderItems;

    private int totalFulfillmentCharges;


    private Date submitDate;




    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getOrderNumber() {
        return orderNumber;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSubTotal() {
        return subTotal;
    }

    @Override
    public int getTotal() {
        return total;
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
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    @Override
    public Set<FulfillmentGroup> getFulfillmentGroups() {
        return null;
    }

    @Override
    public Set<CandidateOrderOffer> getCandidateOrderOffers() {
        return null;
    }

    @Override
    public Date getSubmitDate() {
        return null;
    }

    @Override
    public int getTotalFulfillmentCharges() {
        return 0;
    }

    @Override
    public OrderPayment getOrderPayment() {
        return null;
    }

    @Override
    public Set<OrderAdjustment> getOrderAdjustments() {
        return null;
    }

    @Override
    public OfferCode getAddedOfferCode() {
        return null;
    }

    @Override
    public String getFulfillmentStatus() {
        return null;
    }

    @Override
    public int getItemAdjustmentsValue() {
        return 0;
    }

    @Override
    public int getOrderAdjustmentsValue() {
        return 0;
    }

    @Override
    public int getTotalAdjustmentsValue() {
        return 0;
    }

    @Override
    public int getProductCount() {
        return 0;
    }

    @Override
    public Currency getCurrency() {
        return null;
    }

    @Override
    public boolean getHasOrderAdjustments() {
        return false;
    }

    @Override
    public Order setId(long id) {
        return null;
    }

    @Override
    public Order setOrderNumber(String orderNumber) {
        return null;
    }

    @Override
    public Order setName(String name) {
        return null;
    }

    @Override
    public Order setSubTotal(int subTotal) {
        return null;
    }

    @Override
    public Order setTotal(int total) {
        return null;
    }

    @Override
    public Order setCustomer(User customer) {
        return null;
    }

    @Override
    public Order setOrderStatus(OrderStatus orderStatus) {
        return null;
    }

    @Override
    public Order setOrderItems(List<OrderItem> orderItems) {
        return null;
    }

    @Override
    public Order setFulfillmentGroups(Set<FulfillmentGroup> fulfillmentGroups) {
        return null;
    }

    @Override
    public Order setCandidateOrderOffers(Set<CandidateOrderOffer> candidateOrderOffers) {
        return null;
    }

    @Override
    public Order setSubmitDate(Date submitDate) {
        return null;
    }

    @Override
    public Order setTotalFulfillmentCharges(int totalFulfillmentCharges) {
        return null;
    }

    @Override
    public Order setOrderPayment(OrderPayment orderPayment) {
        return null;
    }

    @Override
    public Order setOrderAdjustments(Set<OrderAdjustment> orderAdjustments) {
        return null;
    }

    @Override
    public Order setAddedOfferCode(OfferCode addedOfferCode) {
        return null;
    }

    @Override
    public Order setFulfillmentStatus(String fulfillmentStatus) {
        return null;
    }

    @Override
    public Order setItemAdjustmentsValue(int itemAdjustmentsValue) {
        return null;
    }

    @Override
    public Order setOrderAdjustmentsValue(int orderAdjustmentsValue) {
        return null;
    }

    @Override
    public Order setTotalAdjustmentsValue(int totalAdjustmentsValue) {
        return null;
    }

    @Override
    public Order setProductCount(int productCount) {
        return null;
    }

    @Override
    public Order setCurrency(Currency currency) {
        return null;
    }

    @Override
    public Order setOffer(Offer offer) {
        return null;
    }

    @Override
    public Order assignOrderItemsFinalPrice() {
        return null;
    }

    @Override
    public Order addOrderItem(OrderItem orderItem) {
        return null;
    }

    @Override
    public int calculateSubTotal() {
        return 0;
    }

    @Override
    public boolean updatePrices() {
        return false;
    }

    @Override
    public boolean finalizeItemPrices() {
        return false;
    }

    @Override
    public Order addOfferCode(OfferCode addedOfferCode) {
        return null;
    }
}