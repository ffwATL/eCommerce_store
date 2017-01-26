package com.ffwatl.admin.order.domain;

import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OfferImpl;

import javax.persistence.*;

@Entity
@Table(name = "order_item_qualifiers")
public class OrderItemQualifierImpl implements OrderItemQualifier {

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

    @Column(name = "quantity")
    private int quantity;

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
    public int getQuantity() {
        return quantity;
    }

    @Override
    public OrderItemQualifier setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public OrderItemQualifier setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        return this;
    }

    @Override
    public OrderItemQualifier setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Override
    public OrderItemQualifier setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItemQualifierImpl that = (OrderItemQualifierImpl) o;

        if (getId() != that.getId()) return false;
        if (getQuantity() != that.getQuantity()) return false;
        if (getOrderItem() != null ? !getOrderItem().equals(that.getOrderItem()) : that.getOrderItem() != null)
            return false;
        return !(getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getOrderItem() != null ? getOrderItem().hashCode() : 0);
        result = 31 * result + (getOffer() != null ? getOffer().hashCode() : 0);
        result = 31 * result + getQuantity();
        return result;
    }

    @Override
    public String toString() {
        return "OrderItemQualifierImpl{" +
                "id=" + id +
                ", orderItem=" + orderItem +
                ", offer=" + offer +
                ", quantity=" + quantity +
                '}';
    }
}