package com.ffwatl.admin.offer.service;

import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.common.EnumerationType;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An extendible enumeration of offer types.
 */
public class OfferType implements Serializable, EnumerationType, Comparable<OfferType> {
    private static final long serialVersionUID = 1L;

    private static final Map<String, OfferType> TYPES = new LinkedHashMap<>();

    public static final OfferType ORDER_ITEM = new OfferType("ORDER_ITEM",
            new I18n().setLocale_en("Order Item"), 1000);
    public static final OfferType ORDER = new OfferType("ORDER", new I18n().setLocale_en("Order"), 2000);
    public static final OfferType FULFILLMENT_GROUP = new OfferType("FULFILLMENT_GROUP",
            new I18n().setLocale_en("Fulfillment Group"), 3000);

    public static OfferType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private I18n friendlyType;
    private int order;

    public OfferType() {
        //do nothing
    }

    public OfferType(final String type, final I18n friendlyType, int order) {
        this.friendlyType = friendlyType;
        setType(type);
        setOrder(order);
    }

    @Override
    public String getType() {
        return type;
    }
    public int getOrder() {
        return order;
    }

    @Override
    public I18n getFriendlyType() {
        return friendlyType;
    }

    public void setOrder(int order) {
        this.order = order;
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

        OfferType offerType = (OfferType) o;

        if (getOrder() != offerType.getOrder()) return false;
        if (getType() != null ? !getType().equals(offerType.getType()) : offerType.getType() != null) return false;
        return !(getFriendlyType() != null ? !getFriendlyType().equals(offerType.getFriendlyType()) : offerType.getFriendlyType() != null);

    }

    @Override
    public int hashCode() {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + (getFriendlyType() != null ? getFriendlyType().hashCode() : 0);
        result = 31 * result + getOrder();
        return result;
    }

    @Override
    public int compareTo(OfferType o) {
        return o == null ? 1: this.order - o.order;
    }

    @Override
    public String toString() {
        return "OfferType{" +
                "type='" + type + '\'' +
                ", friendlyType=" + friendlyType +
                ", order=" + order +
                '}';
    }
}