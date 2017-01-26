package com.ffwatl.admin.offer.domain;

import com.ffwatl.admin.user.domain.User;

import java.io.Serializable;


public interface CustomerOffer extends Serializable {

    long getId();

    User getCustomer();

    Offer getOffer();


    CustomerOffer setId(long id);

    CustomerOffer setCustomer(User customer);

    CustomerOffer setOffer(Offer offer);
}
