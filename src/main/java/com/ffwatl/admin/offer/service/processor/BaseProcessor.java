package com.ffwatl.admin.offer.service.processor;

import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.user.domain.User;

import java.util.List;



public interface BaseProcessor {

    List<Offer> filterOffers(List<Offer> offers, User customer);

}