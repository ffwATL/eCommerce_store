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
public interface PromotionQualifier extends Serializable {

    Offer getPromotion();

    Rule getItemRule();

    int getQuantity();

    int getFinalizedQuantity();


    void setPromotion(Offer promotion);

    void setItemRule(Rule itemRule);

    void setQuantity(int quantity);

    void setFinalizedQuantity(int finalizedQuantity);

    void incrementQuantity(int quantity);

    PromotionQualifier copy();

    void resetQty(int qty);

    PromotionQualifier split(int splitItemQty);

    boolean isFinalized();
}