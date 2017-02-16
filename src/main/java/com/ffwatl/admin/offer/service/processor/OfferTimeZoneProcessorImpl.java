package com.ffwatl.admin.offer.service.processor;


import com.ffwatl.admin.offer.domain.Offer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Service("offer_timezone_processor")
public class OfferTimeZoneProcessorImpl implements OfferTimeZoneProcessor {

    private static final Logger logger = LogManager.getLogger();

    @Resource(name = "localDateTimeFormatter")
    private DateTimeFormatter dateTimeFormatter;

    @Override
    public TimeZone getTimeZone(Offer offer) {
        return TimeZone.getDefault();
    }

    @Override
    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }
}