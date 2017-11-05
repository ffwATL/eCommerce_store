package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.i18n.domain.I18n;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author ffw_ATL.
 */
@Entity
@Table(name = "product_attribute_names")
public class AttributeNameImpl implements AttributeName {

    @Id
    private long id;

    @Embedded
    private I18n name;

    @Override
    public I18n getName() {
        return name;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public AttributeName setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public AttributeName setName(I18n name) {
        this.name = name;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttributeNameImpl that = (AttributeNameImpl) o;

        if (getId() != that.getId()) return false;
        return getName() != null ? getName().equals(that.getName()) : that.getName() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AttributeNameImpl{" +
                "id='" + id + '\'' +
                ", name=" + name +
                '}';
    }

    @Override
    public int compareTo(AttributeName o) {
        return this.name.compareTo(o.getName());
    }
}