package com.ffwatl.admin.catalog.domain;



public interface Field {

    long getId();
    String getName();
    String getValue();

    Field setId(long id);
    Field setName(String name);
    Field setValue(String value);

}
