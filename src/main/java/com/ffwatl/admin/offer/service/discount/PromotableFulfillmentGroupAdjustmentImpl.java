package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.service.OfferDiscountType;
import com.ffwatl.common.currency.CurrencyUtil;

public class PromotableFulfillmentGroupAdjustmentImpl implements PromotableFulfillmentGroupAdjustment {

    private static final long serialVersionUID = 1L;

    private PromotableCandidateFulfillmentGroupOffer promotableCandidateFulfillmentGroupOffer;
    private PromotableFulfillmentGroup promotableFulfillmentGroup;
    private int saleAdjustmentValue;
    private int retailAdjustmentValue;
    private int adjustmentValue;
    private boolean appliedToSalePrice;

    public PromotableFulfillmentGroupAdjustmentImpl(
            PromotableCandidateFulfillmentGroupOffer promotableCandidateFulfillmentGroupOffer,
            PromotableFulfillmentGroup fulfillmentGroup) {
        this.promotableCandidateFulfillmentGroupOffer = promotableCandidateFulfillmentGroupOffer;
        this.promotableFulfillmentGroup = fulfillmentGroup;
        computeAdjustmentValues();
    }

    /*
     * Calculates the value of the adjustment for both retail and sale prices.
     * If either adjustment is greater than the item value, it will be set to the
     * currentItemValue (e.g. no adjustment can cause a negative value).
     */
    private void computeAdjustmentValues() {
        saleAdjustmentValue = 0;
        retailAdjustmentValue = 0;

        Offer offer = promotableCandidateFulfillmentGroupOffer.getOffer();

        Currency from = offer.getOfferCurrency();
        Currency to = promotableFulfillmentGroup.getFulfillmentGroup().getOrder().getCurrency();

        int currentPriceDetailSalesValue = CurrencyUtil.getAmountInAnotherCurrency(
                promotableFulfillmentGroup.calculatePriceWithAdjustments(true), from, to);
        int currentPriceDetailRetailValue = CurrencyUtil.getAmountInAnotherCurrency(
                promotableFulfillmentGroup.calculatePriceWithAdjustments(false), from, to);

        retailAdjustmentValue = PromotableOfferUtility.computeAdjustmentValue(currentPriceDetailRetailValue,
                offer.getValue(), offer.getDiscountType());

        if (offer.isValidOnSale()) {
            saleAdjustmentValue = PromotableOfferUtility.computeAdjustmentValue(currentPriceDetailSalesValue,
                    offer.getValue(), offer.getDiscountType());
        }
    }

    private int computeAdjustmentValue(int currentPriceDetailValue) {
        Offer offer = promotableCandidateFulfillmentGroupOffer.getOffer();
        OfferDiscountType discountType = offer.getDiscountType();

        Currency from = offer.getOfferCurrency();
        Currency to = promotableFulfillmentGroup.getFulfillmentGroup().getOrder().getCurrency();

        // converting offer value from it's currency to the order's currency
        int adjustmentValue = CurrencyUtil.getAmountInAnotherCurrency(offer.getValue(), from, to);

        return PromotableOfferUtility.computeAdjustmentValue(currentPriceDetailValue, adjustmentValue, discountType);
    }

    @Override
    public Offer getOffer() {
        return promotableCandidateFulfillmentGroupOffer.getOffer();
    }

    @Override
    public PromotableFulfillmentGroup getPromotableFulfillmentGroup() {
        return promotableFulfillmentGroup;
    }

    @Override
    public PromotableCandidateFulfillmentGroupOffer getPromotableCandidateFulfillmentGroupOffer() {
        return promotableCandidateFulfillmentGroupOffer;
    }

    @Override
    public int getSaleAdjustmentValue() {
        return saleAdjustmentValue;
    }

    @Override
    public int getRetailAdjustmentValue() {
        return retailAdjustmentValue;
    }

    @Override
    public int getAdjustmentValue() {
        return adjustmentValue;
    }

    @Override
    public boolean isCombinable() {
        return getOffer().isCombinableWithOtherOffers();
    }

    @Override
    public boolean isTotalitarian() {
        return getOffer().isTotalitarianOffer();
    }

    @Override
    public void finalizeAdjustment(boolean useSaleAdjustments) {
        appliedToSalePrice = useSaleAdjustments;
        if (useSaleAdjustments) {
            adjustmentValue = saleAdjustmentValue;
        } else {
            adjustmentValue = retailAdjustmentValue;
        }
    }

    @Override
    public boolean isAppliedToSalePrice() {
        return appliedToSalePrice;
    }

    @Override
    public String toString() {
        return "PromotableFulfillmentGroupAdjustmentImpl{" +
                "promotableCandidateFulfillmentGroupOffer=" + promotableCandidateFulfillmentGroupOffer +
                ", promotableFulfillmentGroup=" + promotableFulfillmentGroup +
                ", saleAdjustmentValue=" + saleAdjustmentValue +
                ", retailAdjustmentValue=" + retailAdjustmentValue +
                ", adjustmentValue=" + adjustmentValue +
                ", appliedToSalePrice=" + appliedToSalePrice +
                '}';
    }
}