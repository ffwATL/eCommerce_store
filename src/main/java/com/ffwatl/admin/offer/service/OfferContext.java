package com.ffwatl.admin.offer.service;

import com.ffwatl.common.classloader.ThreadLocalManager;


public class OfferContext {

    private static final ThreadLocal<OfferContext> OFFER_CONTEXT = ThreadLocalManager.createThreadLocal(OfferContext.class);

    public static OfferContext getOfferContext() {
        return OFFER_CONTEXT.get();
    }

    public static void setOfferContext(OfferContext offerContext) {
        OFFER_CONTEXT.set(offerContext);
    }

    protected Boolean executePromotionCalculation = true;

    public Boolean getExecutePromotionCalculation() {
        return executePromotionCalculation;
    }

    public void setExecutePromotionCalculation(Boolean executePromotionCalculation) {
        this.executePromotionCalculation = executePromotionCalculation;
    }
}