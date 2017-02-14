package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.common.currency.CurrencyUtil;
import com.ffwatl.common.rule.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotableCandidateOrderOfferImpl implements PromotableCandidateOrderOffer{

    private static final long serialVersionUID = 1L;

    private Map<Rule, List<PromotableOrderItem>> candidateQualifiersMap = new HashMap<>();

    private Offer offer;

    private PromotableOrder promotableOrder;

    private int potentialSavings;


    public PromotableCandidateOrderOfferImpl(PromotableOrder promotableOrder, Offer offer) {
        assert(offer != null);
        assert(promotableOrder != null);
        this.promotableOrder = promotableOrder;
        this.offer = offer;
        calculatePotentialSavings();
    }

    /**
     * Instead of calculating the potential savings, you can specify an override of this value.
     * This is currently coded only to work if the promotableOrder's isIncludeOrderAndItemAdjustments flag
     * is true.
     */
    public PromotableCandidateOrderOfferImpl(PromotableOrder promotableOrder, Offer offer, int potentialSavings) {
        this(promotableOrder, offer);
        if (promotableOrder.isIncludeOrderAndItemAdjustments()) {
            this.potentialSavings = potentialSavings;
        }
    }

    private void calculatePotentialSavings() {
        Currency from = offer.getOfferCurrency();
        Currency to = promotableOrder.getOrderCurrency();

        int amountBeforeAdjustments = promotableOrder.calculateSubtotalWithoutAdjustments();
        int potentialSavings = CurrencyUtil.getAmountInAnotherCurrency(offer.getValue(), from, to);

        potentialSavings = PromotableOfferUtility.computeAdjustmentValue(amountBeforeAdjustments, potentialSavings, offer.getDiscountType());
        this.potentialSavings = potentialSavings;
    }

    @Override
    public PromotableOrder getPromotableOrder() {
        return promotableOrder;
    }

    @Override
    public Offer getOffer() {
        return offer;
    }

    @Override
    public int getPotentialSavings() {
        return potentialSavings;
    }

    @Override
    public Map<Rule, List<PromotableOrderItem>> getCandidateQualifiersMap() {
        return candidateQualifiersMap;
    }

    @Override
    public boolean isTotalitarian() {
        return offer.isTotalitarianOffer();
    }

    @Override
    public boolean isCombinable() {
        return offer.isCombinableWithOtherOffers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PromotableCandidateOrderOfferImpl that = (PromotableCandidateOrderOfferImpl) o;

        if (getPotentialSavings() != that.getPotentialSavings()) return false;
        if (getCandidateQualifiersMap() != null ? !getCandidateQualifiersMap().equals(that.getCandidateQualifiersMap()) : that.getCandidateQualifiersMap() != null)
            return false;
        if (getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null) return false;
        return !(getPromotableOrder() != null ? !getPromotableOrder().equals(that.getPromotableOrder()) : that.getPromotableOrder() != null);

    }

    @Override
    public int hashCode() {
        int result = getCandidateQualifiersMap() != null ? getCandidateQualifiersMap().hashCode() : 0;
        result = 31 * result + (getOffer() != null ? getOffer().hashCode() : 0);
        result = 31 * result + (getPromotableOrder() != null ? getPromotableOrder().hashCode() : 0);
        result = 31 * result + getPotentialSavings();
        return result;
    }

    @Override
    public String toString() {
        return "PromotableCandidateOrderOfferImpl{" +
                "candidateQualifiersMap=" + candidateQualifiersMap +
                ", offer=" + offer +
                ", promotableOrder=" + promotableOrder +
                ", potentialSavings=" + potentialSavings +
                '}';
    }
}