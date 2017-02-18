package com.ffwatl.admin.offer.service;


import com.ffwatl.admin.offer.domain.Offer;

public class FulfillmentGroupOfferPotentialImpl implements FulfillmentGroupOfferPotential{

    private Offer offer;
    private int totalSavings;
    private int priority;
    private int hashcode;


    @Override
    public Offer getOffer() {
        return offer;
    }

    @Override
    public int getTotalSavings() {
        return totalSavings;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public FulfillmentGroupOfferPotential setOffer(Offer offer) {
        this.offer = offer;
        hashCode();
        return this;
    }

    @Override
    public FulfillmentGroupOfferPotential setTotalSavings(int totalSavings) {
        this.totalSavings = totalSavings;
        return this;
    }

    @Override
    public FulfillmentGroupOfferPotential setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FulfillmentGroupOfferPotentialImpl that = (FulfillmentGroupOfferPotentialImpl) o;

        return !(getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null);

    }

    @Override
    public int hashCode() {
        if (hashcode == 0){
            this.hashcode = getOffer() != null ? getOffer().hashCode() : 0;
        }
        return hashcode;
    }

    @Override
    public String toString() {
        return "FulfillmentGroupOfferPotentialImpl{" +
                "offer=" + offer +
                ", totalSavings=" + totalSavings +
                ", priority=" + priority +
                '}';
    }
}