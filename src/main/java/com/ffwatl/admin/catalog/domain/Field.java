package com.ffwatl.admin.catalog.domain;



public interface Field {

    long getId();

    String getName();

    String getValue();

    FieldType getFieldType();

    Field setId(long id);

    Field setName(String name);

    Field setValue(String value);

    Field setFieldType(FieldType fieldType);

}