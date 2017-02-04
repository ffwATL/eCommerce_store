package com.ffwatl.admin.order.dao;


import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.extension.ExtensionHandler;
import com.ffwatl.common.extension.ExtensionResultStatusType;

import java.util.List;

public interface OrderDaoExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType attachAdditionalDataToNewCart(User customer, Order cart);

    ExtensionResultStatusType processPostSaveNewCart(User customer, Order cart);

    ExtensionResultStatusType applyAdditionalOrderLookupFilter(User customer, String name, List<Order> orders);
}