package com.ffwatl.admin.offer.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ffw_ATL on 29-Dec-16.
 */
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
    Offer setActive(boolean active);
}