package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.offer.domain.OrderItemPriceDetailAdjustment;



public class PromotableOrderItemPriceDetailAdjustmentImpl implements PromotableOrderItemPriceDetailAdjustment{

    private static final long serialVersionUID = 1L;

    private PromotableCandidateItemOffer promotableCandidateItemOffer;
    private PromotableOrderItemPriceDetail promotableOrderItemPriceDetail;
    private int saleAdjustmentValue;
    private int retailAdjustmentValue;
    private int adjustmentValue;
    private boolean appliedToSalePrice;
    private Offer offer;

    public PromotableOrderItemPriceDetailAdjustmentImpl(PromotableCandidateItemOffer promotableCandidateItemOffer,
                                                        PromotableOrderItemPriceDetail orderItemPriceDetail) {
        assert (promotableCandidateItemOffer != null);
        assert (orderItemPriceDetail != null);
        this.promotableCandidateItemOffer = promotableCandidateItemOffer;
        this.promotableOrderItemPriceDetail = orderItemPriceDetail;
        this.offer = promotableCandidateItemOffer.getOffer();
        computeAdjustmentValues();
    }

    public PromotableOrderItemPriceDetailAdjustmentImpl(OrderItemPriceDetailAdjustment itemAdjustment,
                                                        PromotableOrderItemPriceDetail orderItemPriceDetail) {
        assert (orderItemPriceDetail != null);
        adjustmentValue = itemAdjustment.getAdjustmentValue();
        if (itemAdjustment.isAppliedToSalePrice()) {
            saleAdjustmentValue = itemAdjustment.getSalesPriceValue();
            // This will just set to a Money value of zero in the correct currency.
            retailAdjustmentValue = itemAdjustment.getRetailPriceValue();
        } else {
            retailAdjustmentValue = itemAdjustment.getAdjustmentValue();
            // This will just set to a Money value of zero in the correct currency.
            saleAdjustmentValue = itemAdjustment.getSalesPriceValue();
        }
        appliedToSalePrice = itemAdjustment.isAppliedToSalePrice();
        promotableOrderItemPriceDetail = orderItemPriceDetail;
        offer = itemAdjustment.getOffer();
    }

    /*
     * Calculates the value of the adjustment for both retail and sale prices.
     * If either adjustment is greater than the item value, it will be set to the
     * currentItemValue (e.g. no adjustment can cause a negative value).
     */
    protected void computeAdjustmentValues() {
        saleAdjustmentValue = 0;
        retailAdjustmentValue = 0;

        int currentPriceDetailRetailValue = promotableOrderItemPriceDetail.calculateItemUnitPriceWithAdjustments(false);
        int currentPriceDetailSalesValue = promotableOrderItemPriceDetail.calculateItemUnitPriceWithAdjustments(true);
        if (currentPriceDetailSalesValue == 0) {
            currentPriceDetailSalesValue = currentPriceDetailRetailValue;
        }

        int offerUnitValue = offer.getValue();

        retailAdjustmentValue = PromotableOfferUtility.computeAdjustmentValue(currentPriceDetailRetailValue, offerUnitValue, offer.getDiscountType());

        if (offer.isValidOnSale()) {
            saleAdjustmentValue = PromotableOfferUtility.computeAdjustmentValue(currentPriceDetailSalesValue, offerUnitValue, offer.getDiscountType());
        }
    }

    @Override
    public PromotableOrderItemPriceDetail getPromotableOrderItemPriceDetail() {
        return promotableOrderItemPriceDetail;
    }

    @Override
    public Offer getOffer() {
        return offer;
    }

    @Override
    public int getRetailAdjustmentValue() {
        return retailAdjustmentValue;
    }

    @Override
    public int getSaleAdjustmentValue() {
        return saleAdjustmentValue;
    }

    @Override
    public int getAdjustmentValue() {
        return adjustmentValue;
    }

    @Override
    public boolean isAppliedToSalePrice() {
        return appliedToSalePrice;
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
    public long getOfferId() {
        return offer.getId();
    }

    @Override
    public void finalizeAdjustment(boolean useSalePrice) {
        appliedToSalePrice = useSalePrice;
        if (useSalePrice) {
            adjustmentValue = saleAdjustmentValue;
        } else {
            adjustmentValue = retailAdjustmentValue;
        }
    }

    @Override
    public PromotableOrderItemPriceDetailAdjustment copy() {
        PromotableOrderItemPriceDetailAdjustmentImpl newAdjustment = new PromotableOrderItemPriceDetailAdjustmentImpl(
                promotableCandidateItemOffer, promotableOrderItemPriceDetail);
        newAdjustment.adjustmentValue = adjustmentValue;
        newAdjustment.saleAdjustmentValue = saleAdjustmentValue;
        newAdjustment.retailAdjustmentValue = retailAdjustmentValue;
        newAdjustment.appliedToSalePrice = appliedToSalePrice;
        return newAdjustment;
    }

    @Override
    public String toString() {
        return "PromotableOrderItemPriceDetailAdjustmentImpl{" +
                "promotableCandidateItemOffer=" + promotableCandidateItemOffer +
                ", promotableOrderItemPriceDetail=" + promotableOrderItemPriceDetail +
                ", saleAdjustmentValue=" + saleAdjustmentValue +
                ", retailAdjustmentValue=" + retailAdjustmentValue +
                ", adjustmentValue=" + adjustmentValue +
                ", appliedToSalePrice=" + appliedToSalePrice +
                ", offer=" + offer +
                '}';
    }
}