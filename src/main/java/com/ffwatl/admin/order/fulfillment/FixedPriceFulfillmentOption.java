package com.ffwatl.admin.order.fulfillment;

import com.ffwatl.admin.currency.domain.Currency;
import com.ffwatl.admin.order.domain.FulfillmentOption;
import com.ffwatl.admin.pricing.fulfillment.FixedPriceFulfillmentPricingProvider;

/**
 * Used in conjunction with the {@link FixedPriceFulfillmentPricingProvider} to allow for a single price
 * for fulfilling an order (e.g. $5 shipping)
 *
 * @author ffw_ATL.
 */
public interface FixedPriceFulfillmentOption extends FulfillmentOption {

    int getPrice();

    Currency getCurrency();

    void setPrice(int price);

    void setCurrency(Currency currency);
}