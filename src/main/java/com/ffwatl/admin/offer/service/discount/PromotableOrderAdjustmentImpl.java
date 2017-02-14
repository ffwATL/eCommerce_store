package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.common.currency.CurrencyUtil;

public class PromotableOrderAdjustmentImpl implements PromotableOrderAdjustment{

    private static final long serialVersionUID = 1L;

    private PromotableCandidateOrderOffer promotableCandidateOrderOffer;
    private PromotableOrder promotableOrder;
    private int adjustmentValue;
    private Offer offer;


    public PromotableOrderAdjustmentImpl(PromotableCandidateOrderOffer promotableCandidateOrderOffer, PromotableOrder promotableOrder) {
        assert (promotableOrder != null);
        assert (promotableCandidateOrderOffer != null);

        this.promotableCandidateOrderOffer = promotableCandidateOrderOffer;
        this.promotableOrder = promotableOrder;
        this.offer = promotableCandidateOrderOffer.getOffer();
        computeAdjustmentValue();
    }

    public PromotableOrderAdjustmentImpl(PromotableCandidateOrderOffer promotableCandidateOrderOffer,
                                         PromotableOrder promotableOrder, int adjustmentValue) {
        this(promotableCandidateOrderOffer, promotableOrder);
        if (promotableOrder.isIncludeOrderAndItemAdjustments()) {
            this.adjustmentValue = adjustmentValue;
        }
    }

    /*
     * Calculates the value of the adjustment by first getting the current value of the order and then
     * calculating the value of this adjustment.
     *
     * If this adjustment value is greater than the currentOrderValue (e.g. would make the order go negative
     * then the adjustment value is set to the value of the order).
     */
    private void computeAdjustmentValue() {
        adjustmentValue = 0;
        int currentOrderValue = promotableOrder.calculateSubtotalWithAdjustments();

        Currency from = offer.getOfferCurrency();
        Currency to = promotableOrder.getOrderCurrency();

        // We need to convert offer value to the orders currency;
        int offerValue = CurrencyUtil.getAmountInAnotherCurrency(offer.getValue(), from, to);

        // We also need to consider order offers that have been applied when figuring out if the current value of this
        // adjustment will be more than the current subtotal of the order
        currentOrderValue -= promotableOrder.calculateOrderAdjustmentTotal();

        // Note: FIXED_PRICE shouldn't calculate as this is not a valid option for offers.
        adjustmentValue = PromotableOfferUtility.computeAdjustmentValue(currentOrderValue,
                offerValue, offer.getDiscountType());

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
    public int getAdjustmentValue() {
        return adjustmentValue;
    }

    @Override
    public boolean isCombinable() {
        return offer.isCombinableWithOtherOffers();
    }

    @Override
    public boolean isTotalitarian() {
        return offer.isTotalitarianOffer();
    }

    @Override
    public String toString() {
        return "PromotableOrderAdjustmentImpl{" +
                "promotableCandidateOrderOffer=" + promotableCandidateOrderOffer +
                ", promotableOrder=" + promotableOrder +
                ", adjustmentValue=" + adjustmentValue +
                ", offer=" + offer +
                '}';
    }
}