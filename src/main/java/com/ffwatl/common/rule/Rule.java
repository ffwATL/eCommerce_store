package com.ffwatl.common.rule;


public interface Rule {

    long getId();
    String getType();
    String getBoundValue();
    String getFieldName();

    Rule setId(long id);
    Rule setType(String type);
    Rule setBoundValue(String boundValue);
    Rule setFieldName(String fieldName);

}