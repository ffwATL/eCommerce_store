package com.ffwatl.admin.order.service.extension;

import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.common.extension.ExtensionHandler;
import com.ffwatl.common.extension.ExtensionResultStatusType;

/**
 * Extension handler for merge cart
 *
 * @author ffw_ATL.
 */
public interface MergeCartServiceExtensionHandler extends ExtensionHandler {

    ExtensionResultStatusType setNewCartOwnership(Order cart, User customer);

    ExtensionResultStatusType updateMergedOrder(Order cart, User customer);

}