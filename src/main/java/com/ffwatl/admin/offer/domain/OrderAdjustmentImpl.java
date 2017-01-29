package com.ffwatl.admin.offer.domain;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderImpl;

import javax.persistence.*;


@Entity
@Table(name = "order_adjustments")
public class OrderAdjustmentImpl implements OrderAdjustment {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = OrderImpl.class)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(targetEntity = OfferImpl.class, optional=false)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Column(name = "adjustment_reason", nullable=false)
    private String reason;

    @Column(name = "adjustment_value")
    private int adjustmentValue;

    @Override
    public OrderAdjustment init(Order order, Offer offer, String reason) {
        this.order = order;
        this.offer = offer;
        this.reason = reason;
        return this;
    }


    @Override
    public Order getOrder() {
        return this.order;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public Offer getOffer() {
        return this.offer;
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public int getAdjustmentValue() {
        return this.adjustmentValue;
    }

    @Override
    public OrderAdjustment setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public OrderAdjustment setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Override
    public OrderAdjustment setOrder(Order order) {
        this.order = order;
        return this;
    }

    @Override
    public OrderAdjustment setAdjustmentValue(int adjustmentValue) {
        this.adjustmentValue = adjustmentValue;
        return this;
    }

    @Override
    public OrderAdjustment setReason(String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderAdjustmentImpl that = (OrderAdjustmentImpl) o;

        if (getId() != that.getId()) return false;
        if (getAdjustmentValue() != that.getAdjustmentValue()) return false;
        if (getOrder() != null ? !getOrder().equals(that.getOrder()) : that.getOrder() != null) return false;
        if (getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null) return false;
        return !(getReason() != null ? !getReason().equals(that.getReason()) : that.getReason() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getReason() != null ? getReason().hashCode() : 0);
        result = 31 * result + getAdjustmentValue();
        return result;
    }

    @Override
    public String toString() {
        return "OrderAdjustmentImpl{" +
                "id=" + id +
                ", order=" + order.getOrderNumber() +
                ", offer=" + offer.getName() +
                ", reason='" + reason + '\'' +
                ", adjustmentValue=" + adjustmentValue +
                '}';
    }
}