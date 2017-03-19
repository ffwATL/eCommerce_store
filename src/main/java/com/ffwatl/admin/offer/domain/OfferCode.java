package com.ffwatl.admin.offer.domain;

import java.io.Serializable;
import java.util.Date;


public interface OfferCode extends Serializable{

    long getId();

    int getVersion();

    Offer getOffer();

    String getOfferCode();

    Date getStartDate();

    Date getEndDate();

    boolean isActive();

    int getMaxUses();

    OfferCode setId(long id);

    OfferCode setVersion(int version);

    OfferCode setOffer(Offer offer);

    OfferCode setOfferCode(String offerCode);

    OfferCode setStartDate(Date startDate);

    OfferCode setEndDate(Date endDate);

    OfferCode setActive(boolean active);

    OfferCode setMaxUses(int maxUses);
}