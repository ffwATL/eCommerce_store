package com.ffwatl.admin.order.dao;


import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.service.type.FulfillmentGroupStatusType;
import com.ffwatl.common.persistence.FetchMode;

import java.util.List;

public interface FulfillmentGroupDao {

    FulfillmentGroup findFulfillmentGroupById(long id, FetchMode fetchMode);

    FulfillmentGroup save(FulfillmentGroup fulfillmentGroup);

    FulfillmentGroup findDefaultFulfillmentGroupForOrder(Order order, FetchMode fetchMode);

    void delete(FulfillmentGroup fulfillmentGroup);

    FulfillmentGroup create();


    List<FulfillmentGroup> findUnfulfilledFulfillmentGroups(int start, int maxResults, FetchMode fetchMode);

    List<FulfillmentGroup> findUnprocessedFulfillmentGroups(int start, int maxResults, FetchMode fetchMode);

    List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults,
                                                         boolean ascending, FetchMode fetchMode);

    List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults,
                                                         FetchMode fetchMode);

}