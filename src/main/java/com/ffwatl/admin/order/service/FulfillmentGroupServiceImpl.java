package com.ffwatl.admin.order.service;

import com.ffwatl.admin.order.dao.FulfillmentGroupDao;
import com.ffwatl.admin.order.dao.FulfillmentGroupItemDao;
import com.ffwatl.admin.order.domain.FulfillmentGroup;
import com.ffwatl.admin.order.domain.FulfillmentGroupItem;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.domain.OrderItem;
import com.ffwatl.admin.order.service.call.FulfillmentGroupItemRequest;
import com.ffwatl.admin.order.service.call.FulfillmentGroupRequest;
import com.ffwatl.admin.order.service.type.FulfillmentGroupStatusType;
import com.ffwatl.admin.order.service.type.FulfillmentType;
import com.ffwatl.common.persistence.FetchMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ffw_ATL.
 */
@Service("fulfillment_group_service")
@Transactional(readOnly = true)
public class FulfillmentGroupServiceImpl  implements FulfillmentGroupService{

    @Resource(name = "fulfillment_group_dao")
    private FulfillmentGroupDao fulfillmentGroupDao;

    @Resource(name = "fulfillment_group_item_dao")
    private FulfillmentGroupItemDao fulfillmentGroupItemDao;

    @Resource(name = "order_service")
    private OrderService orderService;


    @Override
    public FulfillmentGroup save(FulfillmentGroup fulfillmentGroup) {
        if(fulfillmentGroup == null) throw new IllegalArgumentException("Can't save given FulfillmentGroup: null");
        return fulfillmentGroupDao.save(fulfillmentGroup);
    }

    @Override
    public FulfillmentGroup createEmptyFulfillmentGroup() {
        return fulfillmentGroupDao.create();
    }

    @Override
    public FulfillmentGroup findFulfillmentGroupById(long id, FetchMode fetchMode) {
        if(id < 1) throw new IllegalArgumentException("Invalid id were given: " + id);

        if (fetchMode == null) {
            fetchMode = FetchMode.DEFAULT;
        }
        return fulfillmentGroupDao.findFulfillmentGroupById(id, fetchMode);
    }

    @Override
    @Transactional
    public void delete(FulfillmentGroup fulfillmentGroup) {
        if (fulfillmentGroup != null) {
            fulfillmentGroupDao.delete(fulfillmentGroup);
        }
    }

    @Override
    @Transactional
    public FulfillmentGroup addFulfillmentGroupToOrder(FulfillmentGroupRequest fgRequest, boolean priceOrder) {
        if(fgRequest == null) {
            throw new IllegalArgumentException("Can not add FulfillmentGroup to Order because of invalid FulfillmentGroupRequest: null");
        }

        FulfillmentGroup fg = fulfillmentGroupDao.create()
                .setAddress(fgRequest.getAddress())
                .setOrder(fgRequest.getOrder())
                .setFulfillmentOption(fgRequest.getFulfillmentOption())
                .setType(fgRequest.getFulfillmentType());

        List<FulfillmentGroupItemRequest> fulfillmentGroupItemRequestList = fgRequest.getFulfillmentGroupItemRequests();

        for (int i = 0; i < fulfillmentGroupItemRequestList.size(); i++) {
            FulfillmentGroupItemRequest fgItemRequest = fulfillmentGroupItemRequestList.get(i)
                    .setFulfillmentGroup(fg)
                    .setOrder(fgRequest.getOrder());

            boolean shouldPriceOrder = (priceOrder && (i == (fgRequest.getFulfillmentGroupItemRequests().size() - 1)));
            fg = addItemToFulfillmentGroup(fgItemRequest, shouldPriceOrder);
        }
        return fg;
    }

    @Override
    public FulfillmentGroup addItemToFulfillmentGroup(FulfillmentGroupItemRequest fulfillmentGroupItemRequest,
                                                      boolean priceOrder) {
        return addItemToFulfillmentGroup(fulfillmentGroupItemRequest, priceOrder, true);
    }

    @Override
    @Transactional
    public FulfillmentGroup addItemToFulfillmentGroup(FulfillmentGroupItemRequest fulfillmentGroupItemRequest,
                                                      boolean priceOrder, boolean save) {
        if (priceOrder && !save) {
            throw new IllegalArgumentException("Pricing requires a save");
        }

        Order order = fulfillmentGroupItemRequest.getOrder();
        OrderItem item = fulfillmentGroupItemRequest.getOrderItem();
        FulfillmentGroup fulfillmentGroup = fulfillmentGroupItemRequest.getFulfillmentGroup();

        if (order == null) {
            if (item != null && item.getOrder() != null) {
                order = item.getOrder();
            } else {
                throw new IllegalArgumentException("Order must not be null");
            }
        }

        // 1) Find the order item's existing fulfillment group, if any
        FulfillmentGroup fg = order.getFulfillmentGroup();
        Iterator<FulfillmentGroupItem> itr = fg.getFulfillmentGroupItems().iterator();
        while (itr.hasNext()) {
            FulfillmentGroupItem fgItem = itr.next();
            if (fgItem.getOrderItem().equals(item)) {
                // 2) remove item from it's existing fulfillment group
                itr.remove();
                fulfillmentGroupItemDao.delete(fgItem);
            }
        }

        if (fulfillmentGroup == null) {
            // API user is trying to add an item to a fulfillment group not created
            fulfillmentGroup = fulfillmentGroupDao
                    .create()
                    .setOrder(order);
            fulfillmentGroup = save(fulfillmentGroup);
            order.setFulfillmentGroup(fulfillmentGroup);
        }

        FulfillmentGroupItem fgItem = createFulfillmentGroupItemFromOrderItem(item, fulfillmentGroup,
                                                                             fulfillmentGroupItemRequest.getQuantity());
        if (save) {
            fgItem = fulfillmentGroupItemDao.save(fgItem);
        }

        // 3) add the item to the new fulfillment group
        fulfillmentGroup.addFulfillmentGroupItem(fgItem);

        if (save) {
            order = orderService.save(order, priceOrder);
        }

        return fulfillmentGroup;
    }

    private FulfillmentGroupItem createFulfillmentGroupItemFromOrderItem(OrderItem orderItem, FulfillmentGroup fg,
                                                                         int quantity) {
        FulfillmentGroupItem fgi = fulfillmentGroupItemDao.create();
        fgi.setFulfillmentGroup(fg);
        fgi.setOrderItem(orderItem);
        fgi.setQuantity(quantity);
        return fgi;
    }

    private FetchMode validateFetchMode(FetchMode fetchMode){
        return fetchMode == null ? FetchMode.DEFAULT : fetchMode;
    }

    @Override
    @Transactional
    public Order removeFulfillmentGroupFromOrder(Order order, boolean priceOrder) {
        if(order != null && order.getFulfillmentGroup() != null){
            FulfillmentGroup fg = order.getFulfillmentGroup();
            order.setFulfillmentGroup(null);

            fulfillmentGroupDao.delete(fg);
            order = orderService.save(order, priceOrder);
        }
        return order;
    }

    @Override
    public List<FulfillmentGroup> findUnfulfilledFulfillmentGroups(int start, int maxResults, FetchMode fetchMode) {
        fetchMode = validateFetchMode(fetchMode);
        return fulfillmentGroupDao.findUnfulfilledFulfillmentGroups(start, maxResults, fetchMode);
    }

    @Override
    public List<FulfillmentGroup> findUnprocessedFulfillmentGroups(int start, int maxResults, FetchMode fetchMode) {
        fetchMode = validateFetchMode(fetchMode);
        return fulfillmentGroupDao.findUnprocessedFulfillmentGroups(start, maxResults, fetchMode);
    }

    @Override
    public List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start,
                                                                int maxResults, boolean ascending, FetchMode fetchMode) {
        fetchMode = validateFetchMode(fetchMode);
        return fulfillmentGroupDao.findFulfillmentGroupsByStatus(status, start, maxResults, ascending, fetchMode);
    }

    @Override
    public List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start,
                                                                int maxResults, FetchMode fetchMode) {
        fetchMode = validateFetchMode(fetchMode);
        return fulfillmentGroupDao.findFulfillmentGroupsByStatus(status, start, maxResults, fetchMode);
    }

    @Override
    public boolean isShippable(FulfillmentType fulfillmentType) {
        return fulfillmentType != null && fulfillmentType != FulfillmentType.PHYSICAL_PICKUP;
    }

    @Override
    public List<FulfillmentGroupItem> getFulfillmentGroupItemsForOrderItem(Order order, OrderItem orderItem) {
        List<FulfillmentGroupItem> fgis = new ArrayList<>();

        FulfillmentGroup fulfillmentGroup = order.getFulfillmentGroup();
        fgis.addAll(fulfillmentGroup.getFulfillmentGroupItems()
                .stream()
                .filter(fulfillmentGroupItem -> fulfillmentGroupItem
                        .getOrderItem()
                        .equals(orderItem))
                .collect(Collectors.toList()));
        return fgis;
    }

    @Override
    @Transactional
    public void removeFulfillmentGroupItem(FulfillmentGroupItem fulfillmentGroupItem) {

    }
}