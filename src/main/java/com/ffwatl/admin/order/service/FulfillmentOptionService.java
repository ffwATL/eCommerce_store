package com.ffwatl.admin.order.service;

import com.ffwatl.admin.order.domain.FulfillmentOption;
import com.ffwatl.admin.order.service.type.FulfillmentType;

import java.util.List;

/**
 * @author ffw_ATL.
 */
public interface FulfillmentOptionService {

    FulfillmentOption findFulfillmentOptionById(long fulfillmentOptionId);

    FulfillmentOption save(FulfillmentOption option);

    List<FulfillmentOption> findAllFulfillmentOptions();

    List<FulfillmentOption> findAllFulfillmentOptionsByFulfillmentType(FulfillmentType type);
}