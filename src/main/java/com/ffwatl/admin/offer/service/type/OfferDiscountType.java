package com.ffwatl.admin.offer.service.type;

import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.common.EnumerationType;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * An extendible enumeration of discount types.
 */
public class OfferDiscountType implements Serializable, EnumerationType {

    private static final long serialVersionUID = 1L;
    private static final Map<String, OfferDiscountType> TYPES = new LinkedHashMap<>();

    public static final OfferDiscountType PERCENT_OFF = new OfferDiscountType("PERCENT_OFF",
            new I18n().setLocale_en("Percent Off"));
    public static final OfferDiscountType AMOUNT_OFF = new OfferDiscountType("AMOUNT_OFF",
            new I18n().setLocale_en("Amount Off"));
    public static final OfferDiscountType FIX_PRICE = new OfferDiscountType("FIX_PRICE",
            new I18n().setLocale_en("Fixed Price"));

    private String type;
    private I18n friendlyType;

    public static OfferDiscountType getInstance(final String type) {
        return TYPES.get(type);
    }

    public OfferDiscountType() {
        //do nothing
    }

    public OfferDiscountType(final String type, final I18n friendlyType) {
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

        OfferDiscountType that = (OfferDiscountType) o;

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
        return "OfferDiscountType{" +
                "type='" + type + '\'' +
                ", friendlyType=" + friendlyType +
                '}';
    }
}