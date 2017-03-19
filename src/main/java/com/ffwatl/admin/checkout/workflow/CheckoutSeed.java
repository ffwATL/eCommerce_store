package com.ffwatl.admin.checkout.workflow;

import com.ffwatl.admin.order.domain.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ffw_ATL.
 */
public class CheckoutSeed implements CheckoutResponse {

    private Order order;
    private Map<String, Object> userDefinedFields = new HashMap<>();

    public CheckoutSeed(Order order, Map<String, Object> userDefinedFields) {
        this.order = order;
        this.userDefinedFields = userDefinedFields;
    }

    @Override
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order){
        this.order = order;
    }

    public Map<String, Object> getUserDefinedFields() {
        return userDefinedFields;
    }
}