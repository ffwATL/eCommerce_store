package com.ffwatl.common.rule;


import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.common.EnumerationType;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class RuleType implements Serializable, EnumerationType {

    private static final long serialVersionUID = 1L;
    private static final Map<String, RuleType> TYPES = new LinkedHashMap<>();

    public static final RuleType MIN_SPEND = new RuleType("MIN_SPEND", new I18n()
            .setLocale_en("Minimum spend"));

    public static final RuleType ITEM_BRAND = new RuleType("ITEM_BRAND", new I18n()
            .setLocale_en("Item brand"));

    public static final RuleType SHIPPING_TYPE = new RuleType("SHIPPING_TYPE", new I18n()
            .setLocale_en("Shipping type"));

    private String type;
    private I18n friendlyType;

    public static RuleType getInstance(final String type) {
        return TYPES.get(type);
    }

    public RuleType(){}

    public RuleType(final String type, final I18n friendlyType) {
        this.friendlyType = friendlyType;
        setType(type);
    }

    private void setType(String type) {
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

        RuleType ruleType = (RuleType) o;

        if (getType() != null ? !getType().equals(ruleType.getType()) : ruleType.getType() != null) return false;
        return !(getFriendlyType() != null ? !getFriendlyType().equals(ruleType.getFriendlyType()) : ruleType.getFriendlyType() != null);

    }

    @Override
    public int hashCode() {
        int result = getType() != null ? getType().hashCode() : 0;
        result = 31 * result + (getFriendlyType() != null ? getFriendlyType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RuleType{" +
                "type='" + type + '\'' +
                ", friendlyType=" + friendlyType +
                '}';
    }
}