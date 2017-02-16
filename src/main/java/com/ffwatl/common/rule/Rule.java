package com.ffwatl.common.rule;


public interface Rule {

    long getId();

    String getType();

    String getBoundValue();

    String getFieldName();

    boolean isExcluded();

    ValueType getFieldType();

    Rule setId(long id);

    Rule setType(String type);

    Rule setBoundValue(String boundValue);

    Rule setFieldName(String fieldName);

    Rule setExcluded(boolean excluded);

    Rule setFieldType(ValueType fieldType);
}