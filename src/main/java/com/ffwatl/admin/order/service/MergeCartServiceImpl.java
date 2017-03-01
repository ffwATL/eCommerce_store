package com.ffwatl.admin.order.service;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.service.call.MergeCartResponse;
import com.ffwatl.admin.order.service.call.ReconstructCartResponse;
import com.ffwatl.admin.order.service.extension.MergeCartServiceExtensionManager;
import com.ffwatl.admin.user.domain.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ffw_ATL.
 */
@Service("merge_cart_service")
public class MergeCartServiceImpl implements MergeCartService {

    @Resource(name = "order_service")
    private OrderService orderService;

    @Resource(name = "order_item_service")
    private OrderItemService orderItemService;

    @Resource(name = "fulfillment_group_service")
    private FulfillmentGroupService fulfillmentGroupService;

    @Resource(name = "merge_cart_service_extension_manager")
    private MergeCartServiceExtensionManager extensionManager;


    @Override
    public MergeCartResponse mergeCart(User customer, Order anonymousCart) {
        return mergeCart(customer, anonymousCart, true);
    }

    @Override
    public MergeCartResponse mergeCart(User customer, Order anonymousCart, boolean priceOrder) {
        return null;
    }



    @Override
    public ReconstructCartResponse reconstructCart(User customer, boolean priceOrder) {
        return null;
    }

    @Override
    public ReconstructCartResponse reconstructCart(User customer) {
        return null;
    }
}
