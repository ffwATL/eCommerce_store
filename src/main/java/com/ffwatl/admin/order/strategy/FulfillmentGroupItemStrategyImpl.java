package com.ffwatl.admin.order.strategy;

import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.FulfillmentGroupItem;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.service.FulfillmentGroupService;
import com.ffwatl.admin.order.service.OrderService;
import com.ffwatl.admin.order.service.call.FulfillmentGroupItemRequest;
import com.ffwatl.admin.order.service.workflow.CartOperationRequest;
import com.ffwatl.admin.pricing.exception.PricingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ffw_ATL.
 */
@Service("fulfillment_group_item_strategy")
public class FulfillmentGroupItemStrategyImpl implements FulfillmentGroupItemStrategy {

    private static final Logger LOGGER = LogManager.getLogger();

    @Resource(name = "fulfillment_group_service")
    private FulfillmentGroupService fulfillmentGroupService;

    @Resource(name = "order_service")
    private OrderService orderService;

    private boolean removeEmptyFulfillmentGroups = true;

    @Override
    public CartOperationRequest onItemAdded(CartOperationRequest request) throws PricingException {
        Order order = request.getOrder();
        OrderItem orderItem = request.getOrderItem();
        FulfillmentGroup nullFulfillmentTypeGroup = null;

        //First, let's organize fulfillment groups according to their fulfillment type
        //We'll use the first of each type that we find. Implementors can choose to move groups / items around later.
        if (order.getFulfillmentGroup() != null) {
            FulfillmentGroup group = order.getFulfillmentGroup();

            if (group.getType() == null) {
                nullFulfillmentTypeGroup = group;
            }
        }

        FulfillmentGroup fulfillmentGroup = nullFulfillmentTypeGroup;
        if (fulfillmentGroup == null) {
            fulfillmentGroup = fulfillmentGroupService.createEmptyFulfillmentGroup();
            fulfillmentGroup.setOrder(order);
            order.setFulfillmentGroup(fulfillmentGroup);
        }

        fulfillmentGroup = addItemToFulfillmentGroup(order, orderItem, fulfillmentGroup);
        order = fulfillmentGroup.getOrder();

        return request;
    }

    private FulfillmentGroup addItemToFulfillmentGroup(Order order, OrderItem orderItem, FulfillmentGroup fulfillmentGroup) throws PricingException {
        return this.addItemToFulfillmentGroup(order, orderItem, orderItem.getQuantity(), fulfillmentGroup);
    }

    private FulfillmentGroup addItemToFulfillmentGroup(Order order, OrderItem orderItem, int quantity,
                                                       FulfillmentGroup fulfillmentGroup) throws PricingException {
        FulfillmentGroupItemRequest fulfillmentGroupItemRequest = new FulfillmentGroupItemRequest();
        fulfillmentGroupItemRequest.setOrder(order);
        fulfillmentGroupItemRequest.setOrderItem(orderItem);
        fulfillmentGroupItemRequest.setQuantity(quantity);
        fulfillmentGroupItemRequest.setFulfillmentGroup(fulfillmentGroup);

        return fulfillmentGroupService.addItemToFulfillmentGroup(fulfillmentGroupItemRequest, false, false);
    }

    @Override
    public CartOperationRequest onItemUpdated(CartOperationRequest request) {
        Order order = request.getOrder();
        OrderItem orderItem = request.getOrderItem();
        int orderItemQuantityDelta = request.getOrderItemQuantityDelta();

        if (orderItemQuantityDelta == 0) {
            // If the quantity didn't change, nothing needs to happen
            return request;
        } else {
            List<FulfillmentGroupItem> fgisToDelete = new ArrayList<>();

            fgisToDelete.addAll(updateItemQuantity(order, orderItem, orderItemQuantityDelta));
            request.setFgisToDelete(fgisToDelete);
        }

        return request;
    }

    private List<FulfillmentGroupItem> updateItemQuantity(Order order, OrderItem orderItem,
                                                            int orderItemQuantityDelta) /*throws PricingException*/ {
        List<FulfillmentGroupItem> fgisToDelete = new ArrayList<>();
        boolean done = false;

        if (orderItemQuantityDelta > 0) {
            // If the quantity is now greater, we can simply add quantity to the first
            // fulfillment group we find that has that order item in it.
            FulfillmentGroup fg = order.getFulfillmentGroup();
            for (FulfillmentGroupItem fgItem : fg.getFulfillmentGroupItems()) {
                if (!done && fgItem.getOrderItem().equals(orderItem)) {
                    fgItem.setQuantity(fgItem.getQuantity() + orderItemQuantityDelta);
                    done = true;
                }
            }
        } else {
            // The quantity has been decremented. We must ensure that the appropriate number
            // of fulfillment group items are decremented as well.
            int remainingToDecrement = -1 * orderItemQuantityDelta;

            FulfillmentGroup fg = order.getFulfillmentGroup();
            for (FulfillmentGroupItem fgItem : fg.getFulfillmentGroupItems()) {
                if (fgItem.getOrderItem().equals(orderItem)) {
                    if (!done && fgItem.getQuantity() == remainingToDecrement) {
                        // Quantity matches exactly. Simply remove the item.
                        fgisToDelete.add(fgItem);
                        done = true;
                    } else if (!done && fgItem.getQuantity() > remainingToDecrement) {
                        // We have enough quantity in this fg item to facilitate the entire requsted update
                        fgItem.setQuantity(fgItem.getQuantity() - remainingToDecrement);
                        done = true;
                    } else if (!done) {
                        // We do not have enough quantity. We'll remove this item and continue searching
                        // for the remainder.
                        remainingToDecrement = remainingToDecrement - fgItem.getQuantity();
                        fgisToDelete.add(fgItem);
                    }
                }
            }
        }

        if (!done) {
            throw new IllegalStateException("Could not find matching fulfillment group item for the given order item");
        }

        return fgisToDelete;
    }

    @Override
    public CartOperationRequest onItemRemoved(CartOperationRequest request) {
        Order order = request.getOrder();
        OrderItem orderItem = request.getOrderItem();

        request.getFgisToDelete().addAll(fulfillmentGroupService.getFulfillmentGroupItemsForOrderItem(order, orderItem));

        return request;
    }

    @Override
    public CartOperationRequest verify(CartOperationRequest request) throws PricingException {
        Order order = request.getOrder();

        if (isRemoveEmptyFulfillmentGroups() && order.getFulfillmentGroup() != null) {
            FulfillmentGroup fg = order.getFulfillmentGroup();
            if (fg.getFulfillmentGroupItems() == null || fg.getFulfillmentGroupItems().size() == 0) {
                order.setFulfillmentGroup(null);
                fulfillmentGroupService.delete(fg);
            }
        }

        Map<Long, Integer> oiQuantityMap = new HashMap<>();
        List<OrderItem> expandedOrderItems = order.getOrderItems();
        Map<Long, FulfillmentGroupItem> fgItemMap = new HashMap<>();

        for (OrderItem oi : expandedOrderItems) {
            Integer oiQuantity = oiQuantityMap.get(oi.getId());
            if (oiQuantity == null) {
                oiQuantity = 0;
            }
            oiQuantity += oi.getQuantity();

            oiQuantityMap.put(oi.getId(), oiQuantity);
        }

        // verifying orderItem and FulfillmentGroupItem quantities
        FulfillmentGroup fg = order.getFulfillmentGroup();
        for (FulfillmentGroupItem fgi : fg.getFulfillmentGroupItems()) {
            Long oiId = fgi.getOrderItem().getId();
            Integer oiQuantity = oiQuantityMap.get(oiId);

            if (oiQuantity == null) {
                throw new IllegalStateException("Fulfillment group items and order items are not in sync. OrderItem id: " + oiId);
            }
            oiQuantity -= fgi.getQuantity();
            oiQuantityMap.put(oiId, oiQuantity);
            fgItemMap.put(fgi.getId(), fgi);
        }

        for (Map.Entry<Long, Integer> entry : oiQuantityMap.entrySet()) {
            if (!entry.getValue().equals(0)) {
                LOGGER.warn("Not enough fulfillment group items found for OrderItem id:" + entry.getKey());
                // There are edge cases where the OrderItem and FulfillmentGroupItem quantities can fall out of sync. If this happens
                // we set the FGItem to the correct quantity from the OrderItem and save/reprice the order to synchronize them.
                FulfillmentGroupItem fgItem = fgItemMap.get(entry.getKey());
                for (OrderItem oi : expandedOrderItems) {
                    if (oi.getId() == fgItem.getOrderItem().getId()) {
                        LOGGER.warn("Synchronizing FulfillmentGroupItem to match OrderItem ["
                                + entry.getKey() + "] quantity of : " + oi.getQuantity());
                        fgItem.setQuantity(oi.getQuantity());
                    }
                }
                // We price the order in order to get the right amount after the qty change
                order = orderService.save(order, true);
                request.setOrder(order);
            }
        }

        return request;
    }

    @Override
    public void setRemoveEmptyFulfillmentGroups(boolean removeEmptyFulfillmentGroups) {
        this.removeEmptyFulfillmentGroups = removeEmptyFulfillmentGroups;
    }

    @Override
    public boolean isRemoveEmptyFulfillmentGroups() {
        return removeEmptyFulfillmentGroups;
    }
}