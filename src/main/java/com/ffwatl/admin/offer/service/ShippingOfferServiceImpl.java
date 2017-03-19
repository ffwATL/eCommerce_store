package com.ffwatl.admin.offer.service;

import com.ffwatl.admin.offer.domain.Offer;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.pricing.exception.PricingException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ffw_ATL.
 */
@Service("shipping_offer_service")
public class ShippingOfferServiceImpl implements ShippingOfferService {

    @Resource(name="offer_service")
    protected OfferService offerService;

    @Override
    public void reviewOffers(Order order) throws PricingException {
        List<Offer> offers = offerService.buildOfferListForOrder(order);
        offerService.applyAndSaveFulfillmentGroupOffersToOrder(offers, order);
    }
}