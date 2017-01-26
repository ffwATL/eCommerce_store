package com.ffwatl.admin.offer.domain;

import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.FulfillmentGroupImpl;

import javax.persistence.*;

@Entity
@Table(name = "candidate_fulfillment_offers")
public class CandidateFulfillmentGroupOfferImpl implements CandidateFulfillmentGroupOffer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "discounted_price")
    private int discountedPrice;

    @ManyToOne(targetEntity = FulfillmentGroupImpl.class)
    @JoinColumn(name = "fulfillment_group_id")
    private FulfillmentGroup fulfillmentGroup;

    @ManyToOne(targetEntity = OfferImpl.class, optional = false)
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
    public FulfillmentGroup getFulfillmentGroup() {
        return fulfillmentGroup;
    }

    @Override
    public Offer getOffer() {
        return offer;
    }

    @Override
    public CandidateFulfillmentGroupOffer setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public CandidateFulfillmentGroupOffer setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
        return this;
    }

    @Override
    public CandidateFulfillmentGroupOffer setFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
        this.fulfillmentGroup = fulfillmentGroup;
        return this;
    }

    @Override
    public CandidateFulfillmentGroupOffer setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CandidateFulfillmentGroupOfferImpl that = (CandidateFulfillmentGroupOfferImpl) o;

        if (getId() != that.getId()) return false;
        if (getDiscountedPrice() != that.getDiscountedPrice()) return false;
        if (getFulfillmentGroup() != null ? !getFulfillmentGroup().equals(that.getFulfillmentGroup()) : that.getFulfillmentGroup() != null)
            return false;
        return !(getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getDiscountedPrice();
        result = 31 * result + (getFulfillmentGroup() != null ? getFulfillmentGroup().hashCode() : 0);
        result = 31 * result + (getOffer() != null ? getOffer().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CandidateFulfillmentGroupOfferImpl{" +
                "id=" + id +
                ", discountedPrice=" + discountedPrice +
                ", fulfillmentGroup=" + fulfillmentGroup +
                ", offer=" + offer +
                '}';
    }
}