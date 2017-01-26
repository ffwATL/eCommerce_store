package com.ffwatl.admin.offer.domain;

import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderItemImpl;

import javax.persistence.*;

@Entity
@Table(name = "order_item_adjustment")
public class OrderItemAdjustmentImpl implements OrderItemAdjustment{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = OrderItemImpl.class)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @ManyToOne(targetEntity = OfferImpl.class, optional = false)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Column(name = "adjustment_reason")
    private String reason;

    @Column(name = "adjustment_value")
    private int value;

    @Column(name = "applied_to_sale_price")
    private boolean appliedToSalePrice;

    @Column(name = "retail_price_value")
    private int retailPriceValue;

    @Column(name = "sale_price_value")
    private int salesPriceValue;


    @Override
    public void init(OrderItem orderItem, Offer offer, String reason) {
        this.orderItem = orderItem;
        this.offer = offer;
        this.reason = reason;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public OrderItem getOrderItem() {
        return orderItem;
    }

    @Override
    public Offer getOffer() {
        return offer;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public int getAdjustmentValue() {
        return value;
    }

    @Override
    public boolean isAppliedToSalePrice() {
        return appliedToSalePrice;
    }

    @Override
    public int getRetailPriceValue() {
        return retailPriceValue;
    }

    @Override
    public int getSalesPriceValue() {
        return salesPriceValue;
    }

    @Override
    public OrderItemAdjustment setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public OrderItemAdjustment setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        return this;
    }

    @Override
    public OrderItemAdjustment setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Override
    public OrderItemAdjustment setReason(String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public OrderItemAdjustment setAdjustmentValue(int value) {
        this.value = value;
        return this;
    }

    @Override
    public OrderItemAdjustment setAppliedToSalePrice(boolean appliedToSalePrice) {
        this.appliedToSalePrice = appliedToSalePrice;
        return this;
    }

    @Override
    public OrderItemAdjustment setRetailPriceValue(int retailPriceValue) {
        this.retailPriceValue = retailPriceValue;
        return this;
    }

    @Override
    public OrderItemAdjustment setSalesPriceValue(int salesPriceValue) {
        this.salesPriceValue = salesPriceValue;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItemAdjustmentImpl that = (OrderItemAdjustmentImpl) o;

        if (getId() != that.getId()) return false;
        if (value != that.value) return false;
        if (isAppliedToSalePrice() != that.isAppliedToSalePrice()) return false;
        if (getRetailPriceValue() != that.getRetailPriceValue()) return false;
        if (getSalesPriceValue() != that.getSalesPriceValue()) return false;
        if (getOrderItem() != null ? !getOrderItem().equals(that.getOrderItem()) : that.getOrderItem() != null)
            return false;
        if (getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null) return false;
        return !(getReason() != null ? !getReason().equals(that.getReason()) : that.getReason() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getOrderItem() != null ? getOrderItem().hashCode() : 0);
        result = 31 * result + (getOffer() != null ? getOffer().hashCode() : 0);
        result = 31 * result + (getReason() != null ? getReason().hashCode() : 0);
        result = 31 * result + value;
        result = 31 * result + (isAppliedToSalePrice() ? 1 : 0);
        result = 31 * result + getRetailPriceValue();
        result = 31 * result + getSalesPriceValue();
        return result;
    }

    @Override
    public String toString() {
        return "OrderItemAdjustmentImpl{" +
                "id=" + id +
                ", orderItem=" + orderItem +
                ", offer=" + offer +
                ", reason='" + reason + '\'' +
                ", value=" + value +
                ", appliedToSalePrice=" + appliedToSalePrice +
                ", retailPriceValue=" + retailPriceValue +
                ", salesPriceValue=" + salesPriceValue +
                '}';
    }
}