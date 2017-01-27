package com.ffwatl.admin.order.dao;


import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.FulfillmentGroupItem;

import java.util.List;

public interface FulfillmentGroupItemDao {

    FulfillmentGroupItem findFulfillmentGroupItemById(long id);

    FulfillmentGroupItem save(FulfillmentGroupItem fulfillmentGroupItem);

    List<FulfillmentGroupItem> findFulfillmentGroupItemsForFulfillmentGroup(FulfillmentGroup fulfillmentGroup);

    void delete(FulfillmentGroupItem fulfillmentGroupItem);

    FulfillmentGroupItem create();
}