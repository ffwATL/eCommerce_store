package com.ffwatl.admin.offer.service;


import com.ffwatl.admin.offer.domain.Offer;

public interface FulfillmentGroupOfferPotential {

    Offer getOffer();

    int getTotalSavings();

    int getPriority();


    FulfillmentGroupOfferPotential setOffer(Offer offer);

    FulfillmentGroupOfferPotential setTotalSavings(int totalSavings);

    FulfillmentGroupOfferPotential setPriority(int priority);
}
