package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.common.currency.CurrencyUtil;
import com.ffwatl.common.rule.Rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotableCandidateItemOfferImpl implements PromotableCandidateItemOffer{

    private static final long serialVersionUID = 1L;

    private Offer offer;
    private PromotableOrder promotableOrder;
    private int potentialSavings;
    private int potentialSavingsQtyOne;
    private int uses = 0;

    private Map<Rule, List<PromotableOrderItem>> candidateQualifiersMap = new HashMap<>();
    private Map<Rule, List<PromotableOrderItem>> candidateTargetsMap = new HashMap<>();

    public PromotableCandidateItemOfferImpl(PromotableOrder promotableOrder, Offer offer) {
        assert (offer != null);
        assert (promotableOrder != null);
        this.offer = offer;
        this.promotableOrder = promotableOrder;
    }


    @Override
    public Currency getCurrency() {
        return promotableOrder.getOrderCurrency();
    }

    @Override
    public Map<Rule, List<PromotableOrderItem>> getCandidateQualifiersMap() {
        return candidateQualifiersMap;
    }

    @Override
    public Map<Rule, List<PromotableOrderItem>> getCandidateTargetsMap() {
        return candidateTargetsMap;
    }

    @Override
    public int getPotentialSavings() {
        return potentialSavings;
    }

    @Override
    public int getPotentialSavingsQtyOne() {
        return potentialSavingsQtyOne;
    }

    @Override
    public void setCandidateQualifiersMap(Map<Rule, List<PromotableOrderItem>> candidateItemsMap) {
        this.candidateQualifiersMap = candidateItemsMap;
    }

    @Override
    public void setCandidateTargetsMap(Map<Rule, List<PromotableOrderItem>> candidateItemsMap) {
        this.candidateTargetsMap = candidateItemsMap;
    }

    @Override
    public void setPotentialSavings(int potentialSavings) {
        this.potentialSavings = potentialSavings;
    }

    @Override
    public void setPotentialSavingsQtyOne(int potentialSavingsQtyOne) {
        this.potentialSavingsQtyOne = potentialSavingsQtyOne;
    }

    @Override
    public boolean hasQualifyingItemRule() {
        return (offer.getMatchRules() != null && !offer.getMatchRules().isEmpty());
    }

    @Override
    public int calculateSavingsForOrderItem(PromotableOrderItem orderItem, int qtyToReceiveSavings) {
        Currency from = offer.getOfferCurrency();
        Currency to = promotableOrder.getOrderCurrency();

        int price = CurrencyUtil.getAmountInAnotherCurrency(orderItem.getPriceBeforeAdjustments(getOffer().isValidOnSale()), from, to);
        int offerUnitValue = CurrencyUtil.getAmountInAnotherCurrency(offer.getValue(), from, to);
        int savings = PromotableOfferUtility.computeAdjustmentValue(price, offerUnitValue, offer.getDiscountType());

        return qtyToReceiveSavings * savings;
    }

    @Override
    public int calculateMaximumNumberOfUses() { // TODO: check if we really need it
        return 0;
    }

    @Override
    public int calculateTargetQuantityForTieredOffer() {
        int returnQty = 0;

        for (Rule itemCriteria : getCandidateTargetsMap().keySet()) {
            List<PromotableOrderItem> candidateTargets = getCandidateTargetsMap().get(itemCriteria);
            for (PromotableOrderItem promotableOrderItem : candidateTargets) {
                returnQty += promotableOrderItem.getQuantity();
            }
        }
        return returnQty;
    }

    @Override
    public int calculateMaxUsesForItemCriteria(Rule itemRule, Offer promotion) { // TODO: check if we really need it
        return 0;
    }

    @Override
    public Offer getOffer() {
        return offer;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public void addUse() {
        uses++;
    }

    @Override
    public void resetUses() {
        uses = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PromotableCandidateItemOfferImpl that = (PromotableCandidateItemOfferImpl) o;

        if (getPotentialSavings() != that.getPotentialSavings()) return false;
        if (getPotentialSavingsQtyOne() != that.getPotentialSavingsQtyOne()) return false;
        if (getUses() != that.getUses()) return false;
        if (getOffer() != null ? !getOffer().equals(that.getOffer()) : that.getOffer() != null) return false;
        if (promotableOrder != null ? !promotableOrder.equals(that.promotableOrder) : that.promotableOrder != null)
            return false;
        if (getCandidateQualifiersMap() != null ? !getCandidateQualifiersMap().equals(that.getCandidateQualifiersMap()) : that.getCandidateQualifiersMap() != null)
            return false;
        return !(getCandidateTargetsMap() != null ? !getCandidateTargetsMap().equals(that.getCandidateTargetsMap()) : that.getCandidateTargetsMap() != null);

    }

    @Override
    public int hashCode() {
        int result = getOffer() != null ? getOffer().hashCode() : 0;
        result = 31 * result + (promotableOrder != null ? promotableOrder.hashCode() : 0);
        result = 31 * result + getPotentialSavings();
        result = 31 * result + getPotentialSavingsQtyOne();
        result = 31 * result + getUses();
        result = 31 * result + (getCandidateQualifiersMap() != null ? getCandidateQualifiersMap().hashCode() : 0);
        result = 31 * result + (getCandidateTargetsMap() != null ? getCandidateTargetsMap().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PromotableCandidateItemOfferImpl{" +
                "offer=" + offer +
                ", promotableOrder=" + promotableOrder +
                ", potentialSavings=" + potentialSavings +
                ", potentialSavingsQtyOne=" + potentialSavingsQtyOne +
                ", uses=" + uses +
                ", candidateQualifiersMap=" + candidateQualifiersMap +
                ", candidateTargetsMap=" + candidateTargetsMap +
                '}';
    }
}