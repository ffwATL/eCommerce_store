package com.ffwatl.admin.order.domain;


import com.ffwatl.admin.order.service.FulfillmentGroupStatusType;

import java.io.Serializable;

public interface FulfillmentGroupItem extends Serializable{

    long getId();

    FulfillmentGroup getFulfillmentGroup();

    int getQuantity();

    int getRetailPrice();

    int getTotalItemAmount();

    int getProratedOrderAdjustmentAmount();

    FulfillmentGroupStatusType getStatus();

    /*OrderItem getOrderItem();*/


    FulfillmentGroupItem setId(long id);

    FulfillmentGroupItem setFulfillmentGroup(FulfillmentGroup fulfillmentGroup);

    FulfillmentGroupItem setQuantity(int quantity);

    FulfillmentGroupItem setRetailPrice(int retailPrice);

    FulfillmentGroupItem setTotalItemAmount(int totalItemAmount);

    FulfillmentGroupItem setProratedOrderAdjustmentAmount(int proratedOrderAdjustmentAmount);

    FulfillmentGroupItem setStatus(FulfillmentGroupStatusType status);

    /*FulfillmentGroupItem setOrderItem(OrderItem orderItem);*/

}