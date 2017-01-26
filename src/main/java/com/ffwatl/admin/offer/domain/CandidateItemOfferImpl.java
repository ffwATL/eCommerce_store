package com.ffwatl.admin.offer.domain;

import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.domain.OrderItemImpl;

import javax.persistence.*;

@Entity
@Table(name = "candidate_item_offers")
public class CandidateItemOfferImpl implements CandidateItemOffer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = OrderItemImpl.class)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @ManyToOne(targetEntity = OfferImpl.class, optional = false)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Column(name = "discounted_price")
    private int discountedPrice;

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public OrderItem getOrderItem() {
        return this.orderItem;
    }

    @Override
    public int getDiscountedPrice() {
        return this.discountedPrice;
    }

    @Override
    public Offer getOffer() {
        return this.offer;
    }

    @Override
    public CandidateItemOffer setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public CandidateItemOffer setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
        return this;
    }

    @Override
    public CandidateItemOffer setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Override
    public CandidateItemOffer setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CandidateItemOfferImpl that = (CandidateItemOfferImpl) o;

        if (getId() != that.getId()) return false;
        if (getDiscountedPrice() != that.getDiscountedPrice()) return false;
        if (getOrderItem() != null ? !getOrderItem().equals(that.getOrderItem()) : that.getOrderItem() != null)
            return false;
        return !(getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getOrderItem() != null ? getOrderItem().hashCode() : 0);
        result = 31 * result + (getOffer() != null ? getOffer().hashCode() : 0);
        result = 31 * result + getDiscountedPrice();
        return result;
    }

    @Override
    public String toString() {
        return "CandidateItemOfferImpl{" +
                "id=" + id +
                ", orderItem=" + orderItem +
                ", offer=" + offer +
                ", discountedPrice=" + discountedPrice +
                '}';
    }
}