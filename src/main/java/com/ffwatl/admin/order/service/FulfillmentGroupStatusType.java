package com.ffwatl.admin.order.service;

import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.common.EnumerationType;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;


public class FulfillmentGroupStatusType implements Serializable, EnumerationType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, FulfillmentGroupStatusType> TYPES = new LinkedHashMap<>();

    /**
     * DELIVERED: Used to indicate that all items in the fulfillment group have been delivered. This will generally
     * only be used when there is some integration with a shipping or fulfillment system to indicate that an item
     * has actually been received by the customer.
     */
    public static final FulfillmentGroupStatusType DELIVERED = new FulfillmentGroupStatusType("DELIVERED",
            new I18n().setLocale_en("Delivered"));

    /**
     * CANCELLED: Used to indicate that the fulfillment group will not be shipped.
     */
    public static final FulfillmentGroupStatusType CANCELLED = new FulfillmentGroupStatusType("CANCELLED",
            new I18n().setLocale_en("Cancelled"));

    /**
     * PROCESSING: Used to indicate that the fulfillment group is being processed. For example, during pick or pack
     * processes in a warehouse.
     */
    public static final FulfillmentGroupStatusType PROCESSING = new FulfillmentGroupStatusType("PROCESSING",
            new I18n().setLocale_en("Processing"));

    public static final FulfillmentGroupStatusType NEW = new FulfillmentGroupStatusType("SUBMITTED",
            new I18n().setLocale_en("New"));

    private String type;
    private I18n friendlyType;

    public static FulfillmentGroupStatusType getInstance(final String type) {
        return TYPES.get(type);
    }

    public FulfillmentGroupStatusType() {
        //do nothing
    }

    public FulfillmentGroupStatusType(final String type, final I18n friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public I18n getFriendlyType() {
        return friendlyType;
    }

    private void setType(final String type) {
        this.type = type;
        if (!TYPES.containsKey(type)) {
            TYPES.put(type, this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FulfillmentGroupStatusType that = (FulfillmentGroupStatusType) o;

        if (getType() != null ? !getType().equals(that.getType()) : that.getType() != null) return false;
        return !(getFriendlyType() != null ? !getFriendlyType().equals(that.getFriendlyType()) : that.getFriendlyType() != null);

    }

    @Override
    public int hashCode() {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + (getFriendlyType() != null ? getFriendlyType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FulfillmentGroupStatusType{" +
                "type='" + type + '\'' +
                ", friendlyType=" + friendlyType +
                '}';
    }
}