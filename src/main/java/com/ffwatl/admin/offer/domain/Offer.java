package com.ffwatl.admin.offer.domain;

import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.offer.service.OfferDiscountType;
import com.ffwatl.admin.offer.service.OfferType;

import java.util.Date;


public interface Offer {
    long getId();
    String getName();
    I18n getDescription();
    OfferType getType();
    OfferDiscountType getDiscountType();
    Date getStartDate();
    Date getEndDate();

    int getValue(); //Offer value ie discount value
    boolean isValidOnSale();
    int getMaxUsesPerCustomer();

    Offer setId(long id);
    Offer setName(String name);
    Offer setDescription(I18n description);
    Offer setOfferType(OfferType offerType);
    Offer setDiscountType(OfferDiscountType offerDiscountType);
    Offer setStartDate(Date startDate);
    Offer setEndDate(Date endDate);

    Offer setValue(int value);
    Offer setValidOnSale(boolean validOnSale);
    Offer setMaxUsesPerCustomer(int maxUses);

}