package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.common.rule.Rule;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public interface PromotableCandidateFulfillmentGroupOffer extends Serializable{

    HashMap<Rule, List<PromotableOrderItem>> getCandidateQualifiersMap();

    int getDiscountedPrice();

    Offer getOffer();

    PromotableFulfillmentGroup getFulfillmentGroup();

    int getDiscountedAmount();

    int computeDiscountedAmount();

    void setCandidateQualifiersMap(HashMap<Rule, List<PromotableOrderItem>> candidateItemsMap);
}