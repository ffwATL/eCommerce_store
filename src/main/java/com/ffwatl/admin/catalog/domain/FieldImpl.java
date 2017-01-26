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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String value;

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

    @Override
    public String toString() {
        return "Field{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldImpl field = (FieldImpl) o;
        return getName().equals(field.getName()) && getValue().equals(field.getValue());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getValue().hashCode();
        return result;
    }
}