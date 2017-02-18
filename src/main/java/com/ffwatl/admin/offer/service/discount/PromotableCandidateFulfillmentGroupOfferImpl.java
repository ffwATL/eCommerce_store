package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.service.type.OfferDiscountType;
import com.ffwatl.common.currency.CurrencyUtil;
import com.ffwatl.common.rule.Rule;

import java.util.HashMap;
import java.util.List;

public class PromotableCandidateFulfillmentGroupOfferImpl implements PromotableCandidateFulfillmentGroupOffer{

    private static final long serialVersionUID = 1L;

    private HashMap<Rule, List<PromotableOrderItem>> candidateQualifiersMap = new HashMap<>();
    private Offer offer;
    private PromotableFulfillmentGroup promotableFulfillmentGroup;


    public PromotableCandidateFulfillmentGroupOfferImpl(PromotableFulfillmentGroup promotableFulfillmentGroup, Offer offer) {
        assert(offer != null);
        assert(promotableFulfillmentGroup != null);
        this.offer = offer;
        this.promotableFulfillmentGroup = promotableFulfillmentGroup;

    }


    @Override
    public HashMap<Rule, List<PromotableOrderItem>> getCandidateQualifiersMap() {
        return candidateQualifiersMap;
    }

    @Override
    public int getDiscountedPrice() {
        return (getBasePrice() - computeDiscountedAmount());
    }

    @Override
    public Offer getOffer() {
        return offer;
    }

    @Override
    public PromotableFulfillmentGroup getFulfillmentGroup() {
        return promotableFulfillmentGroup;
    }

    @Override
    public int getDiscountedAmount() {
        return computeDiscountedAmount();
    }

    @Override
    public int computeDiscountedAmount() {
        int discountedAmount = 0;
        int priceToUse = getBasePrice();

        if(priceToUse == 0) {
            return 0;
        }

        OfferDiscountType type = offer.getDiscountType();
        if(type.equals(OfferDiscountType.AMOUNT_OFF)){
            discountedAmount = CurrencyUtil.getAmountInAnotherCurrency(offer.getValue(), offer.getOfferCurrency(),
                    promotableFulfillmentGroup.getFulfillmentGroup().getOrder().getCurrency());
        }else if(type.equals(OfferDiscountType.FIX_PRICE)){
            discountedAmount = priceToUse - CurrencyUtil.getAmountInAnotherCurrency(offer.getValue(), offer.getOfferCurrency(),
                    promotableFulfillmentGroup.getFulfillmentGroup().getOrder().getCurrency());
        }else if(type.equals(OfferDiscountType.PERCENT_OFF)){
            discountedAmount = priceToUse *(offer.getValue() / 100);
        }

        if(discountedAmount > priceToUse) {
            discountedAmount = priceToUse;
        }
        return discountedAmount;
    }

    @Override
    public void setCandidateQualifiersMap(HashMap<Rule, List<PromotableOrderItem>> candidateItemsMap) {
        this.candidateQualifiersMap = candidateItemsMap;
    }

    private int getBasePrice(){
        return promotableFulfillmentGroup.getFulfillmentGroup().getRetailFulfillmentPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PromotableCandidateFulfillmentGroupOfferImpl that = (PromotableCandidateFulfillmentGroupOfferImpl) o;

        if (getCandidateQualifiersMap() != null ? !getCandidateQualifiersMap().equals(that.getCandidateQualifiersMap()) : that.getCandidateQualifiersMap() != null)
            return false;
        if (getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null) return false;
        return !(promotableFulfillmentGroup != null ? !promotableFulfillmentGroup.equals(that.promotableFulfillmentGroup) : that.promotableFulfillmentGroup != null);

    }

    @Override
    public int hashCode() {
        int result = getCandidateQualifiersMap() != null ? getCandidateQualifiersMap().hashCode() : 0;
        result = 31 * result + (getOffer() != null ? getOffer().hashCode() : 0);
        result = 31 * result + (promotableFulfillmentGroup != null ? promotableFulfillmentGroup.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PromotableCandidateFulfillmentGroupOfferImpl{" +
                "candidateQualifiersMap=" + candidateQualifiersMap +
                ", offer=" + offer +
                ", promotableFulfillmentGroup=" + promotableFulfillmentGroup +
                '}';
    }
}