package com.ffwatl.admin.catalog.domain.dto;

import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.ProductAttributeType;
import com.ffwatl.admin.i18n.domain.I18n;


public class ProductAttributeTypeDTO implements ProductAttributeType {

    private long id;
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
    public int compareTo(ProductAttributeType o) {
        if(o == null) return 1;
        if(this.id < o.getId()) return -1;
        return this.id == o.getId() ? 0 : 1;
    }
}
