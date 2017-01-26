package com.ffwatl.admin.payment;


import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.common.EnumerationType;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An extendible enumeration of payment types.
 */
public class PaymentType implements Serializable, EnumerationType {

    private static final long serialVersionUID = 1L;

    private static final Map<String, PaymentType> TYPES = new LinkedHashMap<>();

    private static final PaymentType COD = new PaymentType("C.O.D", new I18n().setLocale_en("Cash on delivery"));
    private static final PaymentType CREDIT_CARD_PAYMENT = new PaymentType("CREDIT_CARD",
            new I18n().setLocale_en("Credit card payment"));

    public static PaymentType getInstance(final String type){
        return TYPES.get(type);
    }

    public PaymentType(){}

    public PaymentType(final String type, final I18n friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
    }

    private String type;

    private I18n friendlyType;



    @Override
    public String getType() {
        return type;
    }

    @Override
    public I18n getFriendlyType() {
        return friendlyType;
    }

    private void setType(String type){
        this.type = type;
        if (!TYPES.containsKey(type)) {
            TYPES.put(type, this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PaymentType that = (PaymentType) o;

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
        return "PaymentType{" +
                "type='" + type + '\'' +
                ", friendlyType=" + friendlyType +
                '}';
    }
}