package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.i18n.domain.I18n;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author ffw_ATL.
 */
@Entity
@Table(name = "product_attribute_template")
public class ProductAttributeTemplateImpl implements ProductAttributeTemplate {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Embedded
    private I18n templateName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = FieldImpl.class)
    @JoinColumn(name = "field_id")
    private List<Field> fields;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, targetEntity = AttributeNameImpl.class)
    @JoinTable(name = "attr_template_attr_name",
            joinColumns = {@JoinColumn(name = "attr_template_id")},
            inverseJoinColumns = {@JoinColumn(name = "attr_name_id")})
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

        ProductAttributeTemplateImpl that = (ProductAttributeTemplateImpl) o;

        if (getId() != that.getId()) return false;
        return getTemplateName() != null ? getTemplateName().equals(that.getTemplateName()) : that.getTemplateName() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getTemplateName() != null ? getTemplateName().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductAttributeTemplateImpl{" +
                "id=" + id +
                ", templateName=" + templateName +
                '}';
    }
}