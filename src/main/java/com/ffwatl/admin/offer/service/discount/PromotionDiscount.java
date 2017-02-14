package com.ffwatl.admin.offer.service.discount;

import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.common.rule.Rule;

import java.io.Serializable;

/**
 * Records the usage of this item as qualifier or target of the promotion. The discount
 * amount will be 0 if this item was only used as a qualifier.
 *
 * @author ffw_ATL
 */
public interface PromotionDiscount extends Serializable {

    Offer getPromotion();

    Rule getItemRule();

    int getQuantity();

    int getFinalizedQuantity();

    PromotableCandidateItemOffer getCandidateItemOffer();

    void setPromotion(Offer promotion);

    void setItemRule(Rule itemRule);

    void setQuantity(int quantity);

    void setFinalizedQuantity(int finalizedQuantity);

    void setCandidateItemOffer(PromotableCandidateItemOffer candidateItemOffer);

    void incrementQuantity(int quantity);

    PromotionDiscount copy();

    void resetQty(int qty);

    PromotionDiscount split(int splitItemQty);

    boolean isFinalized();

}