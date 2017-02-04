package com.ffwatl.admin.order.dao;


import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.extension.AbstractExtensionHandler;
import com.ffwatl.common.extension.ExtensionResultStatusType;

import java.util.List;

public abstract class AbstractOrderDaoExtensionHandler extends AbstractExtensionHandler implements OrderDaoExtensionHandler {


    public ExtensionResultStatusType attachAdditionalDataToNewCart(User customer, Order cart) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    public ExtensionResultStatusType processPostSaveNewCart(User customer, Order cart) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

    public ExtensionResultStatusType applyAdditionalOrderLookupFilter(User customer, String name, List<Order> orders) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}