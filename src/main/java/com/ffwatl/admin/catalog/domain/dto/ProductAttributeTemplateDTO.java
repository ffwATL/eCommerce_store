package com.ffwatl.admin.catalog.domain.dto;

import com.ffwatl.admin.catalog.domain.AttributeName;
import com.ffwatl.admin.catalog.domain.Field;
import com.ffwatl.admin.catalog.domain.ProductAttributeTemplate;
import com.ffwatl.admin.i18n.domain.I18n;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author mmed 11/17/17
 */
public class ProductAttributeTemplateDTO implements ProductAttributeTemplate {

    private long id;

    private I18n templateName;

    private List<Field> fields = new ArrayList<>();

    private Set<AttributeName> attributeNames;



    @Override
    public long getId() {
        return id;
    }

    @Override
    public I18n getTemplateName() {
        return templateName;
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public Set<AttributeName> getAttributeNames() {
        return attributeNames;
    }

    @Override
    public ProductAttributeTemplate setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public ProductAttributeTemplate setTemplateName(I18n name) {
        this.templateName = name;
        return this;
    }

    @Override
    public ProductAttributeTemplate setFields(List<Field> fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public ProductAttributeTemplate setAttributeNames(Set<AttributeName> attributeNames) {
        this.attributeNames = attributeNames;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductAttributeTemplateDTO that = (ProductAttributeTemplateDTO) o;

        if (id != that.id) return false;
        return templateName != null ? templateName.equals(that.templateName) : that.templateName == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (templateName != null ? templateName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductAttributeTemplateDTO{" +
                "id=" + id +
                ", templateName=" + templateName +
                '}';
    }
}