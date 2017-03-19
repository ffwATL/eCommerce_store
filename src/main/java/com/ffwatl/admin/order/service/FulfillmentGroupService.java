package com.ffwatl.admin.order.service;

import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.FulfillmentGroupItem;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.service.call.FulfillmentGroupItemRequest;
import com.ffwatl.admin.order.service.call.FulfillmentGroupRequest;
import com.ffwatl.admin.order.service.type.FulfillmentGroupStatusType;
import com.ffwatl.admin.order.service.type.FulfillmentType;
import com.ffwatl.admin.pricing.exception.PricingException;
import com.ffwatl.common.persistence.FetchMode;

import java.util.List;

/**
 * @author ffw_ATL.
 */
public interface FulfillmentGroupService {

    FulfillmentGroup save(FulfillmentGroup fulfillmentGroup);

    FulfillmentGroup createEmptyFulfillmentGroup();

    FulfillmentGroup findFulfillmentGroupById(long fulfillmentGroupId, FetchMode fetchMode);

    void delete(FulfillmentGroup fulfillmentGroup);

    FulfillmentGroup addFulfillmentGroupToOrder(FulfillmentGroupRequest fulfillmentGroupRequest, boolean priceOrder) throws PricingException/* throws PricingException*/;

    FulfillmentGroup addItemToFulfillmentGroup(FulfillmentGroupItemRequest fulfillmentGroupItemRequest, boolean priceOrder) throws PricingException /*throws PricingException*/;

    FulfillmentGroup addItemToFulfillmentGroup(FulfillmentGroupItemRequest fulfillmentGroupItemRequest, boolean priceOrder, boolean save) throws PricingException /*throws PricingException*/;

    Order removeFulfillmentGroupFromOrder(Order order, boolean priceOrder) throws PricingException /*throws PricingException*/;

    /**
     * Reads FulfillmentGroups whose status is not FULFILLED or DELIVERED.
     *
     * @return FulfillmentGroups whose status is not FULFILLED or DELIVERED.
     */
    List<FulfillmentGroup> findUnfulfilledFulfillmentGroups(int start, int maxResults, FetchMode fetchMode);

    /**
     * Returns FulfillmentGroups whose status is null, or where no processing has yet occured.
     * Default returns in ascending order according to date that the order was created.
     *
     * @return FulfillmentGroups whose status is null, or where no processing has yet occured
     */
    List<FulfillmentGroup> findUnprocessedFulfillmentGroups(int start, int maxResults, FetchMode fetchMode);

    /**
     * Reads FulfillmentGroups by status, either ascending or descending according to the date that
     * the order was created.
     */
    List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start,
                                                         int maxResults, boolean ascending, FetchMode fetchMode);

    /**
     * Reads FulfillmentGroups by status, ascending according to the date that
     * the order was created.
     */
    List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults,
                                                         FetchMode fetchMode);

    /**
     * Determines if a fulfillment group is shippable based on its fulfillment type.
     */
    boolean isShippable(FulfillmentType fulfillmentType);

    /**
     * Finds all FulfillmentGroupItems in the given Order that reference the given OrderItem.
     *
     * @param order
     * @param orderItem
     * @return the list of related FulfillmentGroupItems
     */
    List<FulfillmentGroupItem> getFulfillmentGroupItemsForOrderItem(Order order, OrderItem orderItem);

    void removeFulfillmentGroupItem(FulfillmentGroupItem fulfillmentGroupItem);
}