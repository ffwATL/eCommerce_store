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
    private int value;



    @Override
    public void init(Order order, Offer offer, String reason) {
        this.order = order;
        this.offer = offer;
        this.reason = reason;
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
    public int getValue() {
        return this.value;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderAdjustmentImpl that = (OrderAdjustmentImpl) o;

        if (getId() != that.getId()) return false;
        if (getValue() != that.getValue()) return false;
        if (getOrder() != null ? !getOrder().equals(that.getOrder()) : that.getOrder() != null) return false;
        if (getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null) return false;
        return !(getReason() != null ? !getReason().equals(that.getReason()) : that.getReason() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getOrder() != null ? getOrder().hashCode() : 0);
        result = 31 * result + (getOffer() != null ? getOffer().hashCode() : 0);
        result = 31 * result + (getReason() != null ? getReason().hashCode() : 0);
        result = 31 * result + getValue();
        return result;
    }

    @Override
    public String toString() {
        return "OrderAdjustmentImpl{" +
                "id=" + id +
                ", order=" + order +
                ", offer=" + offer +
                ", reason='" + reason + '\'' +
                ", value=" + value +
                '}';
    }
}