package com.ffwatl.admin.offer.dao;

import com.ffwatl.admin.offer.domain.OfferCode;


public interface OfferCodeDao {


    OfferCode findOfferCodeById(long id);

    OfferCode findOfferCodeByCode(String code);

    OfferCode save(OfferCode offerCode);

    void delete(OfferCode offerCode);

    OfferCode create();
}