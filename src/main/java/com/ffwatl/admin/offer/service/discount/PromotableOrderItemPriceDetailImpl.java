package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.common.rule.Rule;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PromotableOrderItemPriceDetailImpl implements PromotableOrderItemPriceDetail{

    private PromotableOrderItem promotableOrderItem;
    private List<PromotableOrderItemPriceDetailAdjustment> promotableOrderItemPriceDetailAdjustments = new ArrayList<PromotableOrderItemPriceDetailAdjustment>();
    private List<PromotionDiscount> promotionDiscounts = new ArrayList<PromotionDiscount>();
    private List<PromotionQualifier> promotionQualifiers = new ArrayList<PromotionQualifier>();
    private int quantity;
    private boolean useSaleAdjustments = false;
    private boolean adjustmentsFinalized = false;
    private int adjustedTotal;

    public PromotableOrderItemPriceDetailImpl(PromotableOrderItem promotableOrderItem, int quantity) {
        this.promotableOrderItem = promotableOrderItem;
        this.quantity = quantity;
    }

    @Override
    public void addCandidateItemPriceDetailAdjustment(PromotableOrderItemPriceDetailAdjustment itemAdjustment) {
            promotableOrderItemPriceDetailAdjustments.add(itemAdjustment);
    }

    @Override
    public List<PromotableOrderItemPriceDetailAdjustment> getCandidateItemAdjustments() {
        return promotableOrderItemPriceDetailAdjustments;
    }

    @Override
    public boolean isTotalitarianOfferApplied() {
        for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
            if (adjustment.isTotalitarian()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isNonCombinableOfferApplied() {
        for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
            if (!adjustment.isCombinable()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasOrderItemAdjustments() {
        return promotableOrderItemPriceDetailAdjustments.size() > 0;
    }

    private int calculateSaleAdjustmentUnitPrice() {
        int returnPrice = promotableOrderItem.getSalePriceBeforeAdjustments();
        if (returnPrice == 0) {
            returnPrice = promotableOrderItem.getRetailPriceBeforeAdjustments();
        }
        for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
            returnPrice -= adjustment.getSaleAdjustmentValue();
        }
        return returnPrice;
    }

    private int calculateRetailAdjustmentUnitPrice() {
        int returnPrice = promotableOrderItem.getRetailPriceBeforeAdjustments();
        for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
            returnPrice -= adjustment.getRetailAdjustmentValue();
        }
        return returnPrice;
    }

    @Override
    public void chooseSaleOrRetailAdjustments() {
        adjustmentsFinalized = true;
        adjustedTotal = 0;
        this.useSaleAdjustments = Boolean.FALSE;
        int salePriceBeforeAdjustments = promotableOrderItem.getSalePriceBeforeAdjustments();
        int retailPriceBeforeAdjustments = promotableOrderItem.getRetailPriceBeforeAdjustments();

        if (hasOrderItemAdjustments()) {
            int saleAdjustmentPrice = calculateSaleAdjustmentUnitPrice();
            int retailAdjustmentPrice = calculateRetailAdjustmentUnitPrice();

            if (promotableOrderItem.isOnSale()) {
                if (saleAdjustmentPrice <= retailAdjustmentPrice) {
                    this.useSaleAdjustments = Boolean.TRUE;
                    adjustedTotal = saleAdjustmentPrice;
                } else {
                    adjustedTotal = retailAdjustmentPrice;
                }

                if (adjustedTotal >= salePriceBeforeAdjustments) {
                    // Adjustments are not as good as the sale price.  So, clear them and use the sale price.
                    promotableOrderItemPriceDetailAdjustments.clear();
                    adjustedTotal = salePriceBeforeAdjustments;
                }
            } else {
                if (retailAdjustmentPrice >= promotableOrderItem.getRetailPriceBeforeAdjustments()) {
                    // Adjustments are not as good as the retail price.
                    promotableOrderItemPriceDetailAdjustments.clear();
                    adjustedTotal = retailPriceBeforeAdjustments;
                } else {
                    adjustedTotal = retailAdjustmentPrice;
                }
            }

            if (useSaleAdjustments) {
                removeRetailOnlyAdjustments();
            }

            removeZeroDollarAdjustments(useSaleAdjustments);

            finalizeAdjustments(useSaleAdjustments);
        }

        if (adjustedTotal == 0) {
            if (salePriceBeforeAdjustments != 0) {
                this.useSaleAdjustments = true;
                adjustedTotal = salePriceBeforeAdjustments;
            } else {
                adjustedTotal = retailPriceBeforeAdjustments;
            }
        }

        adjustedTotal *= quantity;

    }

    /**
     * Removes retail only adjustments.
     */
    private void removeRetailOnlyAdjustments() {
        Iterator<PromotableOrderItemPriceDetailAdjustment> adjustments = promotableOrderItemPriceDetailAdjustments.iterator();
        while (adjustments.hasNext()) {
            PromotableOrderItemPriceDetailAdjustment adjustment = adjustments.next();
            if (!adjustment.getOffer().isValidOnSale()) {
                adjustments.remove();
            }
        }
    }

    private void removeZeroDollarAdjustments(boolean useSalePrice) {
        Iterator<PromotableOrderItemPriceDetailAdjustment> adjustments = promotableOrderItemPriceDetailAdjustments.iterator();
        while (adjustments.hasNext()) {
            PromotableOrderItemPriceDetailAdjustment adjustment = adjustments.next();
            if (useSalePrice) {
                if (adjustment.getSaleAdjustmentValue() == 0) {
                    adjustments.remove();
                }
            } else {
                if (adjustment.getRetailAdjustmentValue() == 0) {
                    adjustments.remove();
                }
            }
        }
    }

    private void finalizeAdjustments(boolean useSaleAdjustments) {
        for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
            adjustment.finalizeAdjustment(useSaleAdjustments);
        }
    }

    @Override
    public void removeAllAdjustments() {
        promotableOrderItemPriceDetailAdjustments.clear();
        chooseSaleOrRetailAdjustments();
    }

    @Override
    public List<PromotionDiscount> getPromotionDiscounts() {
        return promotionDiscounts;
    }

    @Override
    public List<PromotionQualifier> getPromotionQualifiers() {
        return promotionQualifiers;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public PromotableOrderItem getPromotableOrderItem() {
        return promotableOrderItem;
    }

    @Override
    public int getQuantityAvailableToBeUsedAsQualifier(PromotableCandidateItemOffer itemOffer) {
        int qtyAvailable = quantity;
        Offer promotion = itemOffer.getOffer();

        // Any quantities of this item that have already received the promotion are not eligible.
        for (PromotionDiscount promotionDiscount : promotionDiscounts) {
            if (promotionDiscount.getPromotion().equals(promotion) || restrictTarget(promotion, false)) {
                qtyAvailable = qtyAvailable - promotionDiscount.getQuantity();
            } else {
                // Item's that receive other discounts might still be allowed to be qualifiers
                if (restrictQualifier(promotionDiscount.getPromotion(), true)) {
                    qtyAvailable = qtyAvailable - promotionDiscount.getQuantity();
                }
            }
        }

        // Any quantities of this item that have already been used as a qualifier for this promotion or for
        // another promotion that has a qualifier type of NONE or TARGET_ONLY cannot be used for this promotion
        for (PromotionQualifier promotionQualifier : promotionQualifiers) {
            if (promotionQualifier.getPromotion().equals(promotion) || restrictQualifier(promotion, false)) {
                qtyAvailable = qtyAvailable - promotionQualifier.getQuantity();
            } else {
                if (restrictQualifier(promotionQualifier.getPromotion(), false)) {
                    qtyAvailable = qtyAvailable - promotionQualifier.getQuantity();
                }
            }
        }
        return qtyAvailable;
    }

    private boolean restrictQualifier(Offer offer, boolean targetType) {
        return false; // FIXME: implement Rules logic
    }

    private boolean restrictTarget(Offer offer, boolean targetType) {
        return false; // FIXME: implement Rules logic
    }

    @Override
    public int getQuantityAvailableToBeUsedAsTarget(PromotableCandidateItemOffer itemOffer) {
        int qtyAvailable = quantity;
        Offer promotion = itemOffer.getOffer();

        // 1. Any quantities of this item that have already received the promotion are not eligible.
        // 2. If this promotion is not combinable then any quantities that have received discounts
        //    from other promotions cannot receive this discount
        // 3. If this promotion is combinable then any quantities that have received discounts from
        //    other combinable promotions are eligible to receive this discount as well
        boolean combinable = promotion.isCombinableWithOtherOffers();
        if (!combinable && isNonCombinableOfferApplied()) {
            return 0;
        }

        // Any quantities of this item that have already received the promotion are not eligible.
        // Also, any quantities of this item that have received another promotion are not eligible
        // if this promotion cannot be combined with another discount.
        for (PromotionDiscount promotionDiscount : promotionDiscounts) {
            if (promotionDiscount.getPromotion().equals(promotion) || restrictTarget(promotion, true)) {
                qtyAvailable = qtyAvailable - promotionDiscount.getQuantity();
            } else {
                // The other promotion is Combinable, but we must make sure that the item qualifier also allows
                // it to be reused as a target.
                if (restrictTarget(promotionDiscount.getPromotion(), true)) {
                    qtyAvailable = qtyAvailable - promotionDiscount.getQuantity();
                }
            }
        }

        // 4.  Any quantities of this item that have been used as a qualifier for this promotion are not eligible as targets
        // 5.  Any quantities of this item that have been used as a qualifier for another promotion that does
        //     not allow the qualifier to be reused must be deduced from the qtyAvailable.
        for (PromotionQualifier promotionQualifier : promotionQualifiers) {
            if (promotionQualifier.getPromotion().equals(promotion) || restrictQualifier(promotion, true)) {
                qtyAvailable = qtyAvailable - promotionQualifier.getQuantity();
            } else {
                if (restrictTarget(promotionQualifier.getPromotion(), false)) {
                    qtyAvailable = qtyAvailable - promotionQualifier.getQuantity();
                }
            }
        }

        return qtyAvailable;
    }

    @Override
    public PromotionQualifier addPromotionQualifier(PromotableCandidateItemOffer itemOffer, Rule rule, int qtyToMarkAsQualifier) {
        PromotionQualifier pq = lookupOrCreatePromotionQualifier(itemOffer);
        pq.incrementQuantity(qtyToMarkAsQualifier);
        pq.setItemRule(rule);
        return pq;
    }

    private PromotionQualifier lookupOrCreatePromotionQualifier(PromotableCandidateItemOffer candidatePromotion) {
        Offer promotion = candidatePromotion.getOffer();
        for (PromotionQualifier pq : promotionQualifiers) {
            if (pq.getPromotion().equals(promotion)) {
                return pq;
            }
        }

        PromotionQualifier pq = new PromotionQualifierImpl();
        pq.setPromotion(promotion);
        promotionQualifiers.add(pq);
        return pq;
    }

    @Override
    public void addPromotionDiscount(PromotableCandidateItemOffer itemOffer, Rule rule, int qtyToMarkAsTarget) {
        PromotionDiscount pd = lookupOrCreatePromotionDiscount(itemOffer);
        if (pd == null) {
            return;
        }
        pd.incrementQuantity(qtyToMarkAsTarget);
        pd.setItemRule(rule);
        pd.setCandidateItemOffer(itemOffer);
    }

    private PromotionDiscount lookupOrCreatePromotionDiscount(PromotableCandidateItemOffer candidatePromotion) {
        Offer promotion = candidatePromotion.getOffer();
        for (PromotionDiscount pd : promotionDiscounts) {
            if (pd.getPromotion().equals(promotion)) {
                return pd;
            }
        }

        PromotionDiscount pd = new PromotionDiscountImpl();
        pd.setPromotion(promotion);

        promotionDiscounts.add(pd);
        return pd;
    }

    @Override
    public int calculateItemUnitPriceWithAdjustments(boolean allowSalePrice) {
        int priceWithAdjustments;
        if (allowSalePrice) {
            priceWithAdjustments = promotableOrderItem.getSalePriceBeforeAdjustments();
            if (priceWithAdjustments == 0) {
                return promotableOrderItem.getRetailPriceBeforeAdjustments();
            }
        } else {
            priceWithAdjustments = promotableOrderItem.getRetailPriceBeforeAdjustments();
        }

        for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
            if (allowSalePrice) {
                priceWithAdjustments -= adjustment.getSaleAdjustmentValue();
            } else {
                priceWithAdjustments -= adjustment.getRetailAdjustmentValue();
            }
        }

        return priceWithAdjustments;
    }

    @Override
    public void finalizeQuantities() {
        for (PromotionDiscount promotionDiscount : promotionDiscounts) {
            promotionDiscount.setFinalizedQuantity(promotionDiscount.getQuantity());
        }
        for (PromotionQualifier promotionQualifier : promotionQualifiers) {
            promotionQualifier.setFinalizedQuantity(promotionQualifier.getQuantity());
        }
    }

    @Override
    public void clearAllNonFinalizedQuantities() {
        Iterator<PromotionQualifier> promotionQualifierIterator = promotionQualifiers.iterator();
        while (promotionQualifierIterator.hasNext()) {
            PromotionQualifier promotionQualifier = promotionQualifierIterator.next();
            if (promotionQualifier.getFinalizedQuantity() == 0) {
                // If there are no quantities of this item that are finalized, then remove the item.
                promotionQualifierIterator.remove();
            } else {
                // Otherwise, set the quantity to the number of finalized items.
                promotionQualifier.setQuantity(promotionQualifier.getFinalizedQuantity());
            }
        }

        Iterator<PromotionDiscount> promotionDiscountIterator = promotionDiscounts.iterator();
        while (promotionDiscountIterator.hasNext()) {
            PromotionDiscount promotionDiscount = promotionDiscountIterator.next();
            if (promotionDiscount.getFinalizedQuantity() == 0) {
                // If there are no quantities of this item that are finalized, then remove the item.
                promotionDiscountIterator.remove();
            } else {
                // Otherwise, set the quantity to the number of finalized items.
                promotionDiscount.setQuantity(promotionDiscount.getFinalizedQuantity());
            }
        }
    }

    @Override
    public String buildDetailKey() {
        List<Long> offerIds = new ArrayList<Long>();
        for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
            Long offerId = adjustment.getOffer().getId();
            offerIds.add(offerId);
        }
        Collections.sort(offerIds);
        return promotableOrderItem.getOrderItem().toString() + offerIds.toString() + useSaleAdjustments;
    }

    @Override
    public int getFinalizedTotalWithAdjustments() {
        chooseSaleOrRetailAdjustments();
        return adjustedTotal;
    }

    @Override
    public int calculateTotalAdjustmentValue() {
        return calculateAdjustmentsUnitValue() * quantity;
    }

    private int calculateAdjustmentsUnitValue() {
        int adjustmentUnitValue = 0;

        for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
            adjustmentUnitValue += adjustment.getAdjustmentValue();
        }

        return adjustmentUnitValue;
    }

    @Override
    public PromotableOrderItemPriceDetail splitIfNecessary() {
        for (PromotionDiscount discount : promotionDiscounts) {
            if (discount.getQuantity() != quantity) {
                Long offerId = discount.getCandidateItemOffer().getOffer().getId();
                Offer offer = discount.getCandidateItemOffer().getOffer();
                return this.split(discount.getQuantity(), offerId,
                        !CollectionUtils.isEmpty(offer.getMatchRules()));
            }
        }
        return null;
    }

    private PromotableOrderItemPriceDetail split(int discountQty, Long offerId, boolean hasQualifiers) {
        int originalQty = quantity;
        quantity = discountQty;

        int splitItemQty = originalQty - discountQty;

        // Create the new item with the correct quantity
        PromotableOrderItemPriceDetail newDetail = promotableOrderItem.createNewDetail(splitItemQty);

        // copy discounts
        for (PromotionDiscount existingDiscount : promotionDiscounts) {
            PromotionDiscount newDiscount = existingDiscount.split(discountQty);
            if (newDiscount != null) {
                newDetail.getPromotionDiscounts().add(newDiscount);
            }
        }

        if (hasQualifiers) {
            Iterator<PromotionQualifier> qualifiers = promotionQualifiers.iterator();
            while (qualifiers.hasNext()) {
                PromotionQualifier currentQualifier = qualifiers.next();
                Long qualifierOfferId = currentQualifier.getPromotion().getId();
                if (qualifierOfferId.equals(offerId) && currentQualifier.getQuantity() <= splitItemQty) {
                    // Remove this one from the original detail
                    qualifiers.remove();
                    newDetail.getPromotionQualifiers().add(currentQualifier);
                } else {
                    PromotionQualifier newQualifier = currentQualifier.split(splitItemQty);
                    newDetail.getPromotionQualifiers().add(newQualifier);
                }
            }
        }

        for (PromotableOrderItemPriceDetailAdjustment existingAdjustment : promotableOrderItemPriceDetailAdjustments) {
            PromotableOrderItemPriceDetailAdjustment newAdjustment = existingAdjustment.copy();
            newDetail.addCandidateItemPriceDetailAdjustment(newAdjustment);
        }

        return newDetail;
    }

    @Override
    public boolean useSaleAdjustments() {
        return useSaleAdjustments;
    }

    @Override
    public boolean isAdjustmentsFinalized() {
        return adjustmentsFinalized;
    }

    @Override
    public void setAdjustmentsFinalized(boolean adjustmentsFinalized) {
        this.adjustmentsFinalized = adjustmentsFinalized;
    }

    @Override
    public PromotableOrderItemPriceDetail shallowCopy() {
        return promotableOrderItem.createNewDetail(quantity);
    }

    @Override
    public PromotableOrderItemPriceDetail copyWithFinalizedData() {

        PromotableOrderItemPriceDetail copyDetail = promotableOrderItem.createNewDetail(quantity);

        for (PromotionDiscount existingDiscount : promotionDiscounts) {
            if (existingDiscount.isFinalized()) {
                PromotionDiscount newDiscount = existingDiscount.copy();
                copyDetail.getPromotionDiscounts().add(newDiscount);
            }
        }

        for (PromotionQualifier existingQualifier : promotionQualifiers) {
            if (existingQualifier.isFinalized()) {
                PromotionQualifier newQualifier = existingQualifier.copy();
                copyDetail.getPromotionQualifiers().add(newQualifier);
            }
        }

        for (PromotableOrderItemPriceDetailAdjustment existingAdjustment : promotableOrderItemPriceDetailAdjustments) {
            PromotableOrderItemPriceDetailAdjustment newAdjustment = existingAdjustment.copy();
            copyDetail.addCandidateItemPriceDetailAdjustment(newAdjustment);
        }

        return copyDetail;
    }

    @Override
    public String toString() {
        return "PromotableOrderItemPriceDetailImpl{" +
                "promotableOrderItem=" + promotableOrderItem +
                ", promotableOrderItemPriceDetailAdjustments=" + promotableOrderItemPriceDetailAdjustments +
                ", promotionDiscounts=" + promotionDiscounts +
                ", promotionQualifiers=" + promotionQualifiers +
                ", quantity=" + quantity +
                ", useSaleAdjustments=" + useSaleAdjustments +
                ", adjustmentsFinalized=" + adjustmentsFinalized +
                ", adjustedTotal=" + adjustedTotal +
                '}';
    }
}