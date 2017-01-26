package com.ffwatl.admin.catalog.domain;


import com.ffwatl.admin.i18n.domain.I18n;

import javax.persistence.*;

@Entity
@Table(name = "product_attribute_types")
public class ProductAttributeTypeImpl implements ProductAttributeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private I18n name;

    private CommonCategory cat;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public CommonCategory getCat() {
        return cat;
    }

    @Override
    public I18n getName() {
        return name;
    }

    @Override
    public ProductAttributeType setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public ProductAttributeType setCat(CommonCategory cat) {
        this.cat = cat;
        return this;
    }

    @Override
    public ProductAttributeType setName(I18n name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "ProductAttributeTypeImpl{" +
                "id=" + id +
                ", name=" + name +
                ", cat=" + cat +
                '}';
    }

    @Override
    public int compareTo(ProductAttributeType o) {
        if(o == null) return 1;
        if(this.id < o.getId()) return -1;
        return this.id == o.getId() ? 0 : 1;
    }

}