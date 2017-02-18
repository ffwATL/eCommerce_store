package com.ffwatl.admin.offer.service.type;

import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.common.EnumerationType;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An extendible enumeration of offer rule types.
 */
public class OfferRuleType implements Serializable, EnumerationType{

    private static final long serialVersionUID = 1L;

    private static final Map<String, OfferRuleType> TYPES = new LinkedHashMap<>();

    public static final OfferRuleType ORDER = new OfferRuleType("ORDER", new I18n().setLocale_en("Order"));
    public static final OfferRuleType FULFILLMENT_GROUP = new OfferRuleType("FULFILLMENT_GROUP",
            new I18n().setLocale_en("Fulfillment Group"));
    public static final OfferRuleType CUSTOMER = new OfferRuleType("CUSTOMER", new I18n().setLocale_en("Customer"));
    public static final OfferRuleType TIME = new OfferRuleType("TIME", new I18n().setLocale_en("Time"));
    public static final OfferRuleType REQUEST = new OfferRuleType("REQUEST", new I18n().setLocale_en("Request"));

    public static OfferRuleType getInstance(final String type) {
        return TYPES.get(type);
    }

    private String type;
    private I18n friendlyType;

    public OfferRuleType() {
        //do nothing
    }

    public OfferRuleType(final String type, final I18n friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
    }

    private void setType(final String type) {
        this.type = type;
        if (!TYPES.containsKey(type)) {
            TYPES.put(type, this);
        }
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public I18n getFriendlyType() {
        return friendlyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OfferRuleType that = (OfferRuleType) o;

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
        return "OfferRuleType{" +
                "type='" + type + '\'' +
                ", friendlyType=" + friendlyType +
                '}';
    }
}