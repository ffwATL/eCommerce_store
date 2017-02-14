package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.common.rule.Rule;

public class PromotionDiscountImpl implements PromotionDiscount{

    private static final long serialVersionUID = 1L;

    private PromotableCandidateItemOffer candidateItemOffer;
    private Offer promotion;
    private Rule itemRule;
    private int quantity;
    private int finalizedQuantity;

    @Override
    public Offer getPromotion() {
        return promotion;
    }

    @Override
    public Rule getItemRule() {
        return itemRule;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public int getFinalizedQuantity() {
        return finalizedQuantity;
    }

    @Override
    public PromotableCandidateItemOffer getCandidateItemOffer() {
        return candidateItemOffer;
    }

    @Override
    public void setPromotion(Offer promotion) {
        this.promotion = promotion;
    }

    @Override
    public void setItemRule(Rule itemRule) {
        this.itemRule = itemRule;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public void setFinalizedQuantity(int finalizedQuantity) {
        this.finalizedQuantity = finalizedQuantity;
    }

    @Override
    public void setCandidateItemOffer(PromotableCandidateItemOffer candidateItemOffer) {
        this.candidateItemOffer = candidateItemOffer;
    }

    @Override
    public void incrementQuantity(int quantity) {
        this.quantity += quantity;
    }

    @Override
    public PromotionDiscount copy() {
        PromotionDiscount pd = new PromotionDiscountImpl();
        pd.setItemRule(itemRule);
        pd.setPromotion(promotion);
        pd.setQuantity(quantity);
        pd.setFinalizedQuantity(finalizedQuantity);
        pd.setCandidateItemOffer(candidateItemOffer);
        return pd;
    }

    @Override
    public void resetQty(int qty) {
        quantity = qty;
        finalizedQuantity = qty;
    }

    @Override
    public PromotionDiscount split(int splitQty) {
        PromotionDiscount returnDiscount = copy();
        int originalQty = finalizedQuantity;

        setFinalizedQuantity(splitQty);
        setQuantity(splitQty);

        int newDiscountQty = originalQty - splitQty;
        if (newDiscountQty == 0) {
            return null;
        } else {
            returnDiscount.setQuantity(newDiscountQty);
            returnDiscount.setFinalizedQuantity(newDiscountQty);
        }
        return returnDiscount;
    }

    @Override
    public boolean isFinalized() {
        return quantity == finalizedQuantity;
    }

    @Override
    public String toString() {
        return "PromotionDiscountImpl{" +
                "candidateItemOffer=" + candidateItemOffer +
                ", promotion=" + promotion +
                ", itemRule=" + itemRule +
                ", quantity=" + quantity +
                ", finalizedQuantity=" + finalizedQuantity +
                '}';
    }
}