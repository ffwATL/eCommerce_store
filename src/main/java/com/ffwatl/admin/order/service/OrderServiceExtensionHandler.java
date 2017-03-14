package com.ffwatl.admin.order.service;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.service.call.OrderItemRequestDTO;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.extension.ExtensionHandler;
import com.ffwatl.common.extension.ExtensionResultHolder;
import com.ffwatl.common.extension.ExtensionResultStatusType;

/**
 * @author ffw_ATL.
 */
public interface OrderServiceExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType attachAdditionalDataToNewNamedCart(User customer, Order cart);

    ExtensionResultStatusType preValidateCartOperation(Order cart, ExtensionResultHolder erh);

    ExtensionResultStatusType preValidateUpdateQuantityOperation(Order cart, OrderItemRequestDTO dto,
                                                                        ExtensionResultHolder erh);

    /**
     * Can be used to attach or update fields must prior to saving an order.
     * @return
     */
    ExtensionResultStatusType attachAdditionalDataToOrder(Order order, boolean priceOrder);
}