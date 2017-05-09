package com.ffwatl.common.rule;


import com.ffwatl.admin.offer.domain.Offer;

public interface Rule {

    long getId();

    Offer getOffer();

    String getType();

    String getBoundValue();

    String getFieldName();

    boolean isExcluded();

    ValueType getFieldType();

    Rule setId(long id);

    Rule setOffer(Offer offer);

    Rule setType(String type);

    Rule setBoundValue(String boundValue);

    Rule setFieldName(String fieldName);

    Rule setExcluded(boolean excluded);

    Rule setFieldType(ValueType fieldType);
}