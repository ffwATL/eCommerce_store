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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldDTO fieldDTO = (FieldDTO) o;

        if (getId() != fieldDTO.getId()) return false;
        if (getName() != null ? !getName().equals(fieldDTO.getName()) : fieldDTO.getName() != null) return false;
        return !(getValue() != null ? !getValue().equals(fieldDTO.getValue()) : fieldDTO.getValue() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
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