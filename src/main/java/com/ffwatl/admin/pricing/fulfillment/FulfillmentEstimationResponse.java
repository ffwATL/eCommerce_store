package com.ffwatl.admin.pricing.fulfillment;

import com.ffwatl.admin.order.domain.FulfillmentOption;

import java.util.Map;

/**
 * DTO to allow FulfillmentProcessors to respond to estimation requests for a particular FulfillmentGroup
 * for a particular FulfillmentOptions
 *
 * @author ffw_ATL.
 */
public class FulfillmentEstimationResponse {

    protected Map<? extends FulfillmentOption, Integer> fulfillmentOptionPrices;

    public Map<? extends FulfillmentOption, Integer> getFulfillmentOptionPrices() {
        return fulfillmentOptionPrices;
    }

    public void setFulfillmentOptionPrices(Map<? extends FulfillmentOption, Integer> fulfillmentOptionPrices) {
        this.fulfillmentOptionPrices = fulfillmentOptionPrices;
    }
}