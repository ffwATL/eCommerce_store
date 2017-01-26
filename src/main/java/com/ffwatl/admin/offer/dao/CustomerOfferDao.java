package com.ffwatl.admin.offer.dao;


import com.ffwatl.admin.offer.domain.CustomerOffer;
import com.ffwatl.admin.user.domain.User;

import java.util.List;

public interface CustomerOfferDao {

    CustomerOffer readCustomerOfferById(long customerOfferId);

    List<CustomerOffer> readCustomerOffersByCustomer(User customer);

    CustomerOffer save(CustomerOffer customerOffer);

    void delete(CustomerOffer customerOffer);

    CustomerOffer create();
}