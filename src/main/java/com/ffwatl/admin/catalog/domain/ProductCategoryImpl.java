package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.UserImpl;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ffw_ATL
 * This entity class is for handling groups of items information. Main goal is to
 * group items by their common identity. This table is static, all the data uploads
 * only once. But if needed you can manually add new group through console.
 */
@Entity
@Table(name = "product_category")
public class ProductCategoryImpl implements Serializable, ProductCategory {

    /**
     * Product Category identifier.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    /**
     * Product Category's depth level value.
     */
    @Column(name = "level")
    private int level;

    /**
     * Group name object that contains different language translations.
     */
    @Embedded
    private I18n groupName;

    @OneToOne(cascade = CascadeType.MERGE, targetEntity = UserImpl.class)
    @JoinColumn(name = "user_id")
    private User createdBy;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = ProductCategoryImpl.class)
    @JoinColumn(name = "child_id")
    private List<ProductCategory> child = new ArrayList<>();

    @Column(name="description", length = 2048)
    private String description;

    @Column(name = "weight")
    private int weight;

    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = ProductAttributeTemplateImpl.class)
    @JoinColumn(name = "attr_template_id")
    private ProductAttributeTemplate attributeTemplate;


    public User getCreatedBy() {
        return createdBy;
    }

    public int getWeight() {
        return weight;
    }

    public long getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public List<ProductCategory> getChild() {
        return child;
    }

    public I18n getGroupName() {
        return groupName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public ProductAttributeTemplate getProductAttributeTemplate() {
        return attributeTemplate;
    }
    @Override
    public ProductCategory setId(long id) {
        this.id = id;
        return this;
    }
    @Override
    public ProductCategory setGroupName(I18n groupName) {
        this.groupName = groupName;
        return this;
    }
    @Override
    public ProductCategory setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        return this;
    }
    @Override
    public ProductCategory setDescription(String description) {
        this.description = description;
        return this;
    }
    @Override
    public ProductCategory setChild(List<ProductCategory> child) {
        this.child = child;
        return this;
    }
    @Override
    public ProductCategory setLevel(int level) {
        this.level = level;
        return this;
    }
    @Override
    public ProductCategory setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public ProductCategory setProductAttributeTemplate(ProductAttributeTemplate attributeTemplate) {
        this.attributeTemplate = attributeTemplate;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductCategoryImpl that = (ProductCategoryImpl) o;

        if (getId() != that.getId()) return false;
        if (getLevel() != that.getLevel()) return false;
        if (getWeight() != that.getWeight()) return false;
        return getGroupName() != null ? getGroupName().equals(that.getGroupName()) : that.getGroupName() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getLevel();
        result = 31 * result + (getGroupName() != null ? getGroupName().hashCode() : 0);
        result = 31 * result + getWeight();
        return result;
    }

    @Override
    public int compareTo(ProductCategory o) {
        if(o == null) return 1;
        return groupName.compareTo(o.getGroupName());
    }

    @Override
    public String toString() {
        return "ProductCategoryImpl{" +
                "id=" + id +
                ", level=" + level +
                ", groupName=" + groupName +
                ", createdBy=" + createdBy +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", attributeTemplate=" + attributeTemplate +
                '}';
    }
}