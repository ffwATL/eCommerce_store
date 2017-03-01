package com.ffwatl.admin.order.dao;


import com.ffwatl.admin.order.domain.FulfillmentOption;
import com.ffwatl.admin.order.service.type.FulfillmentType;

import java.util.List;

public interface FulfillmentOptionDao {

    FulfillmentOption findFulfillmentOptionById(long id);

    FulfillmentOption save(FulfillmentOption option);

    List<FulfillmentOption> findAllFulfillmentOptions();

    List<FulfillmentOption> findAllFulfillmentOptionsByFulfillmentType(FulfillmentType type);
}