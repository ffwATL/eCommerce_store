package com.ffwatl.admin.order.service.type;

import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.common.EnumerationType;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An extendible enumeration of fulfillment group types.
 */
public class FulfillmentType implements Serializable, EnumerationType {

    private static final long serialVersionUID = 1L;
    private static final Map<String, FulfillmentType> TYPES = new LinkedHashMap<>();

    public static final FulfillmentType PHYSICAL_SHIP = new FulfillmentType("PHYSICAL_SHIP",
            new I18n().setLocale_en("Physical Ship"));
    public static final FulfillmentType PHYSICAL_PICKUP = new FulfillmentType("PHYSICAL_PICKUP",
            new I18n().setLocale_en("Physical Pickup"));

    private String type;
    private I18n friendlyType;

    public static FulfillmentType getInstance(final String type) {
        return TYPES.get(type);
    }

    public FulfillmentType() {
        //do nothing
    }

    public FulfillmentType(final String type, final I18n friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public I18n getFriendlyType() {
        return this.friendlyType;
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

        FulfillmentType that = (FulfillmentType) o;

        if (!getType().equals(that.getType())) return false;
        return getFriendlyType().equals(that.getFriendlyType());

    }

    @Override
    public int hashCode() {
        int result = getType().hashCode();
        result = 31 * result + getFriendlyType().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "FulfillmentType{" +
                "type='" + type + '\'' +
                ", friendlyType=" + friendlyType +
                '}';
    }
}