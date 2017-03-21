package com.ffwatl.admin.order.domain;




import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.offer.domain.*;
import com.ffwatl.admin.payment.domain.OrderPayment;
import com.ffwatl.admin.payment.domain.OrderPaymentImpl;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.UserImpl;

import javax.annotation.PostConstruct;
import javax.persistence.*;

import java.util.*;


@Entity
@Table(name = "orders")
public class OrderImpl implements Order {

    private static final long serialVersionUID = 1L;

    @PostConstruct
    public void init(){
        if(orderItems != null){
            for(OrderItem oi: orderItems){
                if(oi.getOrder() == null){
                    oi.setOrder(this);
                }
            }
        }

        if(fulfillmentGroup != null && fulfillmentGroup.getOrder() == null){
            fulfillmentGroup.setOrder(this);
        }
    }

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
    private OrderStatus orderStatus = OrderStatus.SUBMITTED;

    @OneToMany(mappedBy = "order",
               fetch = FetchType.LAZY,
               targetEntity = OrderItemImpl.class,
               cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "order", targetEntity = FulfillmentGroupImpl.class, cascade = CascadeType.ALL)
    private FulfillmentGroup fulfillmentGroup;

    @OneToMany(mappedBy = "order",
               fetch = FetchType.LAZY,
               targetEntity = CandidateOrderOfferImpl.class,
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private Set<CandidateOrderOffer> candidateOrderOffers = new HashSet<>();

    @OneToMany(mappedBy = "order",
               fetch = FetchType.LAZY,
               targetEntity = OrderAdjustmentImpl.class,
               cascade = { CascadeType.ALL },
               orphanRemoval = true)
    private Set<OrderAdjustment> orderAdjustments = new HashSet<>();

    @Column(name = "submit_date")
    private Date submitDate;

    @Column(name = "total_fulfillment_charges")
    private int totalFulfillmentCharges;

    @ManyToOne(targetEntity = OrderPaymentImpl.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_payment_id")
    private OrderPayment orderPayment;

    @ManyToOne(targetEntity = OfferCodeImpl.class)
    @JoinColumn(name = "offer_code_id")
    private OfferCode offerCode;

    @Column(name = "currency")
    private Currency currency;



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
    public FulfillmentGroup getFulfillmentGroup() {
        return fulfillmentGroup;
    }

    @Override
    public Set<CandidateOrderOffer> getCandidateOrderOffers() {
        return candidateOrderOffers;
    }

    @Override
    public Set<OrderAdjustment> getOrderAdjustments() {
        return orderAdjustments;
    }

    @Override
    public Date getSubmitDate() {
        return submitDate;
    }

    @Override
    public int getTotalFulfillmentCharges() {
        return totalFulfillmentCharges;
    }

    @Override
    public OrderPayment getOrderPayment() {
        return orderPayment;
    }

    @Override
    public OfferCode getOfferCode() {
        return offerCode;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public String getFulfillmentStatus() {
        return fulfillmentGroup.getStatus().getType();
    }

    @Override
    public int getItemAdjustmentsValue() {
        int adjustmentValue = 0;
        for(OrderItem orderItem: orderItems){
            adjustmentValue+=orderItem.getTotalPrice();
        }
        return adjustmentValue;
    }

    @Override
    public int getFulfillmentGroupAdjustmentsValue() {
        return fulfillmentGroup.getFulfillmentGroupAdjustmentsValue();
    }

    @Override
    public int getOrderAdjustmentsValue() {
        int adjustmentValue = 0;
        for(OrderAdjustment orderAdjustment: orderAdjustments){
            adjustmentValue += orderAdjustment.getAdjustmentValue();
        }
        return adjustmentValue;
    }

    @Override
    public int getTotalAdjustmentsValue() {
        int totalAdjustmentValue = getItemAdjustmentsValue();
        totalAdjustmentValue += getFulfillmentGroupAdjustmentsValue();
        totalAdjustmentValue += getOrderAdjustmentsValue();
        return totalAdjustmentValue;
    }

    @Override
    public int getProductCount() {
        return orderItems.size();
    }

    @Override
    public boolean getHasOrderAdjustments() {
        return getOrderAdjustmentsValue() > 0;
    }

    @Override
    public Order setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Order setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
        return this;
    }

    @Override
    public Order setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Order setSubTotal(int subTotal) {
        this.subTotal = subTotal;
        return this;
    }

    @Override
    public Order setTotal(int total) {
        this.total = total;
        return this;
    }

    @Override
    public Order setCustomer(User customer) {
        this.customer = customer;
        return this;
    }

    @Override
    public Order setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    @Override
    public Order setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

    @Override
    public Order setFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
        this.fulfillmentGroup = fulfillmentGroup;
        return this;
    }

    @Override
    public Order setCandidateOrderOffers(Set<CandidateOrderOffer> candidateOrderOffers) {
        this.candidateOrderOffers = candidateOrderOffers;
        return this;
    }

    @Override
    public Order setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
        return this;
    }

    @Override
    public Order setTotalFulfillmentCharges(int totalFulfillmentCharges) {
        this.totalFulfillmentCharges = totalFulfillmentCharges;
        return this;
    }

    @Override
    public Order setOrderPayment(OrderPayment orderPayment) {
        this.orderPayment = orderPayment;
        return this;
    }

    @Override
    public Order setOrderAdjustments(Set<OrderAdjustment> orderAdjustments) {
        this.orderAdjustments = orderAdjustments;
        return this;
    }

    @Override
    public Order setOfferCode(OfferCode offerCode) {
        this.offerCode = offerCode;
        return this;
    }

    @Override
    public Order setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public Order addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        return this;
    }

    @Override
    public void removeLastOrderItem() {
        checkCollectionIsNotEmpty(orderItems);

        orderItems.remove(orderItems.size() - 1);
    }

    @Override
    public void removeFirstOrderItem() {
        checkCollectionIsNotEmpty(orderItems);
        orderItems.remove(0);
    }

    @Override
    public void removeOrderItem(OrderItem orderItem) {
        if(orderItem == null) {
            throw new IllegalArgumentException("Wrong argument is given: null");
        }
        checkCollectionIsNotEmpty(orderItems);
        orderItems.remove(orderItem);
    }

    @Override
    public void removeOrderItem(int index){
        if(index < 0) throw new IllegalArgumentException("Wrong index is given. Index: " + index);
        checkCollectionIsNotEmpty(orderItems);
        orderItems.remove(index);
    }

    @Override
    public void removeAllOrderItems() {
        orderItems.clear();
    }

    @Override
    public int calculateSubTotal() {
        int subTotal = 0;
        for(OrderItem orderItem: orderItems){
            subTotal += orderItem.getTotalPrice();
        }
        return subTotal;
    }

    private void checkCollectionIsNotEmpty(Collection<?> collection){
        if(collection.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException(collection.getClass().getName() +
                    " - is empty, nothing to remove :(");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderImpl order = (OrderImpl) o;

        if (getId() != order.getId()) return false;
        if (getSubTotal() != order.getSubTotal()) return false;
        if (getTotal() != order.getTotal()) return false;
        if (getTotalFulfillmentCharges() != order.getTotalFulfillmentCharges()) return false;
        if (getOrderNumber() != null ? !getOrderNumber().equals(order.getOrderNumber()) : order.getOrderNumber() != null)
            return false;
        if (getName() != null ? !getName().equals(order.getName()) : order.getName() != null) return false;
        if (getCustomer() != null ? !getCustomer().equals(order.getCustomer()) : order.getCustomer() != null)
            return false;
        if (getOrderStatus() != order.getOrderStatus()) return false;
        if (getOrderItems() != null ? !getOrderItems().equals(order.getOrderItems()) : order.getOrderItems() != null)
            return false;
        if (getFulfillmentGroup() != null ? !getFulfillmentGroup().equals(order.getFulfillmentGroup()) : order.getFulfillmentGroup() != null)
            return false;
        if (getCandidateOrderOffers() != null ? !getCandidateOrderOffers().equals(order.getCandidateOrderOffers()) : order.getCandidateOrderOffers() != null)
            return false;
        if (getOrderAdjustments() != null ? !getOrderAdjustments().equals(order.getOrderAdjustments()) : order.getOrderAdjustments() != null)
            return false;
        if (getSubmitDate() != null ? !getSubmitDate().equals(order.getSubmitDate()) : order.getSubmitDate() != null)
            return false;
        if (getOrderPayment() != null ? !getOrderPayment().equals(order.getOrderPayment()) : order.getOrderPayment() != null)
            return false;
        if (getOfferCode() != null ? !getOfferCode().equals(order.getOfferCode()) : order.getOfferCode() != null)
            return false;
        return getCurrency() == order.getCurrency();

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getOrderNumber() != null ? getOrderNumber().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + getSubTotal();
        result = 31 * result + getTotal();
        result = 31 * result + (getCustomer() != null ? getCustomer().hashCode() : 0);
        result = 31 * result + (getOrderStatus() != null ? getOrderStatus().hashCode() : 0);
        result = 31 * result + (getOrderItems() != null ? getOrderItems().hashCode() : 0);
        result = 31 * result + (getFulfillmentGroup() != null ? getFulfillmentGroup().hashCode() : 0);
        result = 31 * result + (getCandidateOrderOffers() != null ? getCandidateOrderOffers().hashCode() : 0);
        result = 31 * result + (getOrderAdjustments() != null ? getOrderAdjustments().hashCode() : 0);
        result = 31 * result + (getSubmitDate() != null ? getSubmitDate().hashCode() : 0);
        result = 31 * result + getTotalFulfillmentCharges();
        result = 31 * result + (getOrderPayment() != null ? getOrderPayment().hashCode() : 0);
        result = 31 * result + (getOfferCode() != null ? getOfferCode().hashCode() : 0);
        result = 31 * result + (getCurrency() != null ? getCurrency().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderImpl{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", name='" + name + '\'' +
                ", subTotal=" + subTotal +
                ", total=" + total +
                ", customer=" + customer +
                ", orderStatus=" + orderStatus +
                ", orderItems=" + orderItems +
                ", fulfillmentGroup=" + fulfillmentGroup +
                ", candidateOrderOffers=" + candidateOrderOffers +
                ", orderAdjustments=" + orderAdjustments +
                ", submitDate=" + submitDate +
                ", totalFulfillmentCharges=" + totalFulfillmentCharges +
                ", orderPayment=" + orderPayment +
                ", offerCode=" + offerCode +
                ", currency=" + currency +
                '}';
    }
}