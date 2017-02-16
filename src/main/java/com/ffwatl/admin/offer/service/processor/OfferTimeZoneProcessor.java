package com.ffwatl.admin.offer.service.processor;

import com.ffwatl.admin.offer.domain.Offer;

import java.time.format.DateTimeFormatter;
import java.util.TimeZone;


public interface OfferTimeZoneProcessor {

    TimeZone getTimeZone(Offer offer);

    DateTimeFormatter getDateTimeFormatter();

}