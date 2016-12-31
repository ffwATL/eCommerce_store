package com.ffwatl.admin.offer.domain;

import java.io.Serializable;
import java.util.Date;


public interface OfferCode extends Serializable{
    long getId();
    Offer getOffer();
    String getOfferCode();
    Date getStartDate();
    Date getEndDate();
    boolean isActive();

    OfferCode setId(long id);
    OfferCode setOffer(Offer offer);
    OfferCode setOfferCode(String offerCode);
    OfferCode setStartDate(Date startDate);
    OfferCode setEndDate(Date endDate);
    OfferCode setActive(boolean active);
}