package com.ffwatl.admin.offer.domain;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderImpl;

import javax.persistence.*;

@Entity
@Table(name = "candidate_order_offer")
public class CandidateOrderOfferImpl implements CandidateOrderOffer{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "discounted_price")
    private int discountedPrice;

    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = OrderImpl.class)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = OfferImpl.class, optional=false)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int getDiscountedPrice() {
        return discountedPrice;
    }

    @Override
    public Order getOrder() {
        return order;
    }

    @Override
    public Offer getOffer() {
        return offer;
    }

    @Override
    public CandidateOrderOffer setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public CandidateOrderOffer setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
        return this;
    }

    @Override
    public CandidateOrderOffer setOrder(Order order) {
        this.order = order;
        return this;
    }

    @Override
    public CandidateOrderOffer setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CandidateOrderOfferImpl that = (CandidateOrderOfferImpl) o;

        if (getId() != that.getId()) return false;
        if (getDiscountedPrice() != that.getDiscountedPrice()) return false;
        if (getOrder() != null ? !getOrder().equals(that.getOrder()) : that.getOrder() != null) return false;
        return !(getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getDiscountedPrice();
        result = 31 * result + (getOrder() != null ? getOrder().hashCode() : 0);
        result = 31 * result + (getOffer() != null ? getOffer().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CandidateOrderOfferImpl{" +
                "id=" + id +
                ", discountedPrice=" + discountedPrice +
                ", order=" + order +
                ", offer=" + offer +
                '}';
    }
}