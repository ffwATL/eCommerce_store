package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.offer.service.type.OfferDiscountType;

/**
 * Provides shared code for the default implementations of PromotableOrderItemPriceDetailAdjustmentImpl and
 * PromotableOrderAdjustmentImpl
 *
 * @author ffw_ATL
 */
public final class PromotableOfferUtility {

    public static int computeAdjustmentValue(int currentPriceDetailValue, int offerUnitValue,
                                             OfferDiscountType discountType) {
        int adjustmentValue = offerUnitValue;

        if (OfferDiscountType.AMOUNT_OFF.equals(discountType) || OfferDiscountType.FIX_PRICE.equals(discountType)) {
            if(OfferDiscountType.FIX_PRICE.equals(discountType)){
                adjustmentValue = currentPriceDetailValue - adjustmentValue;
            }
        }
        else if (OfferDiscountType.PERCENT_OFF.equals(discountType)) {
            adjustmentValue = currentPriceDetailValue * (offerUnitValue / 100);
        }

        return currentPriceDetailValue < adjustmentValue ? currentPriceDetailValue : adjustmentValue;
    }
}