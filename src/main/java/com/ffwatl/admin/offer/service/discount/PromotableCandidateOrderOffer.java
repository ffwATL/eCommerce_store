package com.ffwatl.admin.offer.service.discount;


import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.common.rule.Rule;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface PromotableCandidateOrderOffer extends Serializable{

    PromotableOrder getPromotableOrder();

    Offer getOffer();

    int getPotentialSavings();

    Map<Rule, List<PromotableOrderItem>> getCandidateQualifiersMap();

    boolean isTotalitarian();

    boolean isCombinable();
}