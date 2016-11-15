package com.ffwatl.admin.catalog.domain.dto;

import com.ffwatl.admin.catalog.domain.Field;



public class FieldDTO implements Field {

    private long id;
    private String name;
    private String value;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public Field setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Field setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Field setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "FieldDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}