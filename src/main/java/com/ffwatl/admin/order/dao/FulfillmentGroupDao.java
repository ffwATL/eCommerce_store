package com.ffwatl.admin.order.dao;


import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.service.FulfillmentGroupStatusType;

import java.util.List;

public interface FulfillmentGroupDao {

    FulfillmentGroup findFulfillmentGroupById(long id);

    FulfillmentGroup save(FulfillmentGroup fulfillmentGroup);

    FulfillmentGroup findDefaultFulfillmentGroupForOrder(Order order);

    void delete(FulfillmentGroup fulfillmentGroup);

    FulfillmentGroup createDefault();

    FulfillmentGroup create();

    List<FulfillmentGroup> findUnfulfilledFulfillmentGroups(int start, int maxResults);

    List<FulfillmentGroup> findPartiallyFulfilledFulfillmentGroups(int start, int maxResults);

    List<FulfillmentGroup> findUnprocessedFulfillmentGroups(int start, int maxResults);

    List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults, boolean ascending);

    List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults);

    int findNextFulfillmentGroupSequnceForOrder(Order order);
}