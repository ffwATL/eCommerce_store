package com.ffwatl.admin.catalog.domain.dto;

import com.ffwatl.admin.catalog.domain.Field;
import com.ffwatl.admin.catalog.domain.FieldType;


public class FieldDTO implements Field {

    private long id;
    private String name;
    private String value;
    private FieldType fieldType;

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
    public FieldType getFieldType() {
        return fieldType;
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
    public Field setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldDTO fieldDTO = (FieldDTO) o;

        if (getName() != null ? !getName().equals(fieldDTO.getName()) : fieldDTO.getName() != null) return false;
        if (getValue() != null ? !getValue().equals(fieldDTO.getValue()) : fieldDTO.getValue() != null) return false;
        return getFieldType() == fieldDTO.getFieldType();

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        result = 31 * result + (getFieldType() != null ? getFieldType().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FieldDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", fieldType=" + fieldType +
                '}';
    }
}