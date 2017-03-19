package com.ffwatl.admin.pricing.fulfillment;

import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.FulfillmentOption;
import com.ffwatl.admin.order.fulfillment.FixedPriceFulfillmentOption;
import com.ffwatl.common.exception.FulfillmentPriceException;

import java.util.HashMap;
import java.util.Set;

/**
 * Processor used in conjunction with {@link FixedPriceFulfillmentOption}. Simply takes the
 * flat rate defined on the option and sets that to the total shipping price of the {@link FulfillmentGroup}
 * @author ffw_ATL.
 */
public class FixedPriceFulfillmentPricingProvider implements FulfillmentPricingProvider {


    @Override
    public FulfillmentGroup calculateCostForFulfillmentGroup(FulfillmentGroup fulfillmentGroup) throws FulfillmentPriceException {
        if (canCalculateCostForFulfillmentGroup(fulfillmentGroup, fulfillmentGroup.getFulfillmentOption())) {
            int price = ((FixedPriceFulfillmentOption) fulfillmentGroup.getFulfillmentOption()).getPrice();
            fulfillmentGroup.setFulfillmentPrice(price);
            fulfillmentGroup.setSaleFulfillmentPrice(price);
            return fulfillmentGroup;
        }

        throw new IllegalArgumentException("Cannot estimate shipping cost for the fulfillment option: "
                + fulfillmentGroup.getFulfillmentOption().getClass().getName());
    }

    @Override
    public boolean canCalculateCostForFulfillmentGroup(FulfillmentGroup fulfillmentGroup, FulfillmentOption option) {
        return (option instanceof FixedPriceFulfillmentOption);
    }

    @Override
    public FulfillmentEstimationResponse estimateCostForFulfillmentGroup(FulfillmentGroup fulfillmentGroup, Set<FulfillmentOption> options) throws FulfillmentPriceException {
        FulfillmentEstimationResponse response = new FulfillmentEstimationResponse();
        HashMap<FulfillmentOption, Integer> shippingPrices = new HashMap<FulfillmentOption, Integer>();
        response.setFulfillmentOptionPrices(shippingPrices);

        for (FulfillmentOption option : options) {
            if (canCalculateCostForFulfillmentGroup(fulfillmentGroup, option)) {
                Integer price = ((FixedPriceFulfillmentOption) option).getPrice();
                shippingPrices.put(option, price);
            }
        }

        return response;
    }
}