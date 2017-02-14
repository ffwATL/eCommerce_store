package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.common.rule.Rule;

public class PromotionQualifierImpl implements PromotionQualifier {

    private static final long serialVersionUID = 1L;

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
    public void incrementQuantity(int quantity) {
        int newQty = this.quantity + quantity;
        if(newQty < 0) {
            throw new IllegalArgumentException("Got negative incremented qty value. " +
                    "Given 'quantity' value is: " + quantity);
        }

        this.quantity = newQty;
    }

    @Override
    public PromotionQualifier copy() {
        PromotionQualifier pq = new PromotionQualifierImpl();
        pq.setItemRule(itemRule);
        pq.setPromotion(promotion);
        pq.setQuantity(quantity);
        pq.setFinalizedQuantity(finalizedQuantity);
        return pq;
    }

    @Override
    public void resetQty(int qty) {
        if(qty < 0) throw new IllegalArgumentException("quantity can't be: null");

        this.quantity = qty;
        this.finalizedQuantity = qty;
    }

    @Override
    public PromotionQualifier split(int splitItemQty) {
        PromotionQualifier returnQualifier = copy();
        int newQty = finalizedQuantity - splitItemQty;

        if (newQty <= 0) {
            throw new IllegalArgumentException("Splitting PromotionQualifier resulted in a negative quantity");
        }

        setFinalizedQuantity(newQty);
        setQuantity(newQty);

        returnQualifier.setQuantity(splitItemQty);
        returnQualifier.setFinalizedQuantity(splitItemQty);

        return returnQualifier;
    }

    @Override
    public boolean isFinalized() {
        return quantity == finalizedQuantity;
    }

    @Override
    public String toString() {
        return "PromotionQualifierImpl{" +
                "promotion=" + promotion +
                ", itemRule=" + itemRule +
                ", quantity=" + quantity +
                ", finalizedQuantity=" + finalizedQuantity +
                '}';
    }
}