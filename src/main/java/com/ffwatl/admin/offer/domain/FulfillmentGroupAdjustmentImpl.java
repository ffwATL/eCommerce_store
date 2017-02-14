package com.ffwatl.admin.offer.domain;


import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.FulfillmentGroupImpl;

import javax.persistence.*;

@Entity
@Table(name = "fulfillment_group_adjustments")
public class FulfillmentGroupAdjustmentImpl implements FulfillmentGroupAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = FulfillmentGroupImpl.class)
    @JoinColumn(name = "fulfillment_group_id")
    private FulfillmentGroup fulfillmentGroup;

    @ManyToOne(targetEntity = OfferImpl.class, optional = false)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Column(name = "adjustment_value")
    private int adjustmentValue;

    @Column(name = "adjustment_reason", nullable = false)
    private String reason;

    @Column(name = "adjustment_currency")
    private Currency currency;


    @Override
    public FulfillmentGroupAdjustment init(FulfillmentGroup fulfillmentGroup, Offer offer, String reason) {
        this.fulfillmentGroup = fulfillmentGroup;
        this.offer = offer;
        this.reason = reason;
        return this;
    }

    @Override
    public long getId() {
        return id;
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
    public int getAdjustmentValue() {
        return adjustmentValue;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public FulfillmentGroupAdjustment setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public FulfillmentGroupAdjustment setFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
        this.fulfillmentGroup = fulfillmentGroup;
        return this;
    }

    @Override
    public FulfillmentGroupAdjustment setOffer(Offer offer) {
        this.offer = offer;
        return this;
    }

    @Override
    public FulfillmentGroupAdjustment setAdjustmentValue(int adjustmentValue) {
        this.adjustmentValue = adjustmentValue;
        return this;
    }

    @Override
    public FulfillmentGroupAdjustment setReason(String reason) {
        this.reason = reason;
        return this;
    }

    @Override
    public FulfillmentGroupAdjustment setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FulfillmentGroupAdjustmentImpl that = (FulfillmentGroupAdjustmentImpl) o;

        if (getId() != that.getId()) return false;
        if (getAdjustmentValue() != that.getAdjustmentValue()) return false;
        if (getFulfillmentGroup() != null ? !getFulfillmentGroup().equals(that.getFulfillmentGroup()) : that.getFulfillmentGroup() != null)
            return false;
        if (getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null) return false;
        if (getReason() != null ? !getReason().equals(that.getReason()) : that.getReason() != null) return false;
        return getCurrency() == that.getCurrency();

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getFulfillmentGroup() != null ? getFulfillmentGroup().hashCode() : 0);
        result = 31 * result + (getOffer() != null ? getOffer().hashCode() : 0);
        result = 31 * result + getAdjustmentValue();
        result = 31 * result + (getReason() != null ? getReason().hashCode() : 0);
        result = 31 * result + (getCurrency() != null ? getCurrency().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FulfillmentGroupAdjustmentImpl{" +
                "id=" + id +
                ", fulfillmentGroup=" + fulfillmentGroup +
                ", offer=" + offer +
                ", adjustmentValue=" + adjustmentValue +
                ", reason='" + reason + '\'' +
                ", currency=" + currency +
                '}';
    }
}