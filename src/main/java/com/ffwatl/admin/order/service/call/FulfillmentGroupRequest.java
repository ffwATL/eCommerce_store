package com.ffwatl.admin.order.service.call;

import com.ffwatl.admin.order.domain.FulfillmentOption;
import com.ffwatl.admin.order.domain.Order;
import com.ffwatl.admin.order.service.type.FulfillmentType;
import com.ffwatl.admin.user.domain.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ffw_ATL.
 */
public class FulfillmentGroupRequest {

    private Address address;
    private Order order;
    private String phoneNumber;
    private FulfillmentOption fulfillmentOption;
    private List<FulfillmentGroupItemRequest> fulfillmentGroupItemRequests = new ArrayList<>();
    private FulfillmentType fulfillmentType;

    public Address getAddress() {
        return address;
    }

    public Order getOrder() {
        return order;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public FulfillmentOption getFulfillmentOption() {
        return fulfillmentOption;
    }

    public List<FulfillmentGroupItemRequest> getFulfillmentGroupItemRequests() {
        return fulfillmentGroupItemRequests;
    }

    public FulfillmentType getFulfillmentType() {
        return fulfillmentType;
    }


    public FulfillmentGroupRequest setAddress(Address address) {
        this.address = address;
        return this;
    }

    public FulfillmentGroupRequest setOrder(Order order) {
        this.order = order;
        return this;
    }

    public FulfillmentGroupRequest setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public FulfillmentGroupRequest setFulfillmentOption(FulfillmentOption fulfillmentOption) {
        this.fulfillmentOption = fulfillmentOption;
        return this;
    }

    public FulfillmentGroupRequest setFulfillmentGroupItemRequests(List<FulfillmentGroupItemRequest> fulfillmentGroupItemRequests) {
        this.fulfillmentGroupItemRequests = fulfillmentGroupItemRequests;
        return this;
    }

    public FulfillmentGroupRequest setFulfillmentType(FulfillmentType fulfillmentType) {
        this.fulfillmentType = fulfillmentType;
        return this;
    }

    @Override
    public String toString() {
        return "FulfillmentGroupRequest{" +
                "address=" + address +
                ", order=" + order +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", fulfillmentOption=" + fulfillmentOption +
                ", fulfillmentGroupItemRequests=" + fulfillmentGroupItemRequests +
                ", fulfillmentType=" + fulfillmentType +
                '}';
    }
}