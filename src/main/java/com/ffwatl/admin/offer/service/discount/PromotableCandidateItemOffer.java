package com.ffwatl.admin.offer.service.discount;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.common.rule.Rule;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface PromotableCandidateItemOffer extends Serializable {

    Currency getCurrency();

    Map<Rule, List<PromotableOrderItem>> getCandidateQualifiersMap();

    Map<Rule, List<PromotableOrderItem>> getCandidateTargetsMap();

    int getPotentialSavings();

    int getPotentialSavingsQtyOne();

    void setCandidateQualifiersMap(Map<Rule, List<PromotableOrderItem>> candidateItemsMap);

    void setCandidateTargetsMap(Map<Rule, List<PromotableOrderItem>> candidateItemsMap);

    void setPotentialSavings(int savings);

    void setPotentialSavingsQtyOne(int potentialSavingsQtyOne);

    boolean hasQualifyingItemRule();

    int calculateSavingsForOrderItem(PromotableOrderItem orderItem, int qtyToReceiveSavings);

    int calculateMaximumNumberOfUses();

    /**
     * Returns the number of item quantities that qualified as targets for
     * this promotion.
     */
    int calculateTargetQuantityForTieredOffer();

    /**
     * Determines the max number of times this Rule might apply. This calculation does
     * not take into account other promotions. It is useful only to assist in prioritizing the order to process
     * the promotions.
     * @return the max number of times this Rule might apply;
     */
    int calculateMaxUsesForItemCriteria(Rule itemRule, Offer promotion);

    Offer getOffer();

    int getUses();

    void addUse();

    /**
     * Resets the uses for this candidate offer item. This is mainly used in the case where we want to calculate savings
     * and then actually apply the promotion to an item. Both scenarios run through the same logic that add uses in order
     * to determine if various quantities of items can be targeted for a particular promotion.
     *
     * @see {@link ItemOfferProcessor#applyAndCompareOrderAndItemOffers(PromotableOrder, List, List)}
     */
    void resetUses();
}