package com.ffwatl.admin.catalog.domain;

import javax.persistence.*;

/**
 * @author ffw_ATL;
 * Entity class is for handling custom numbers of fields in ProductAttributeImpl object. *
 * All the fields must be filled.
 */
@Entity
@Table(name = "fields")
public class FieldImpl implements Field{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "field_name", nullable = false)
    private String name;

    @Column(name = "field_value", nullable = false)
    private String value;

    @Column(name = "field_type", nullable = false)
    private FieldType fieldType;

    /**
     * Returns field Id;
     * @return field Id.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns name of current field;
     * @return field name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns value of current field;
     * @return field value.
     */
    public String getValue() {
        return value;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    /**
     * Setter method for field Id;
     * @param id field id.
     */
    public Field setId(long id) {
        this.id = id;
        return this;
    }

    /**
     * Setter name for current field name;
     * @param name field name.
     */
    public Field setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Setter method for current field value;
     * @param value field value.
     */
    public Field setValue(String value) {
        this.value = value;
        return this;
    }

    public Field setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldImpl field = (FieldImpl) o;

        if (getName() != null ? !getName().equals(field.getName()) : field.getName() != null) return false;
        if (getValue() != null ? !getValue().equals(field.getValue()) : field.getValue() != null) return false;
        return getFieldType() == field.getFieldType();

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
        return "FieldImpl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", fieldType=" + fieldType +
                '}';
    }
}