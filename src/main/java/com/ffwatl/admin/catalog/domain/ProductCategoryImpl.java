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

    private static final long serialVersionUID = 5105837513477369159L;
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

    @Column(name = "weight")
    private int weight;

    @OneToOne(cascade = CascadeType.MERGE, targetEntity = UserImpl.class)
    @JoinColumn(name = "user_id")
    private User createdBy;

    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = ProductCategoryImpl.class)
    @JoinColumn(name = "parent_id")
    private ProductCategory parent;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = ProductCategoryImpl.class)
    private List<ProductCategory> child = new ArrayList<>();

    /**
     * Category name object that contains different language translations.
     */
    @Embedded
    private I18n categoryName;

    @Column(name="description", length = 2048)
    private String description;

    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = ProductAttributeTemplateImpl.class)
    @JoinColumn(name = "attr_template_id")
    private ProductAttributeTemplate attributeTemplate;



    @Override
    public long getId() {
        return id;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public User getCreatedBy() {
        return createdBy;
    }

    @Override
    public ProductCategory getParent() {
        return parent;
    }

    @Override
    public List<ProductCategory> getChild() {
        return child;
    }

    @Override
    public I18n getCategoryName() {
        return categoryName;
    }

    @Override
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
    public ProductCategory setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    @Override
    public ProductCategory setParent(ProductCategory parent) {
        this.parent = parent;
        return this;
    }

    @Override
    public ProductCategory setChild(List<ProductCategory> child) {
        this.child = child;
        return this;
    }

    @Override
    public ProductCategory setCategoryName(I18n categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    @Override
    public ProductCategory setDescription(String description) {
        this.description = description;
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
        return getCategoryName() != null ? getCategoryName().equals(that.getCategoryName()) : that.getCategoryName() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getLevel();
        result = 31 * result + (getCategoryName() != null ? getCategoryName().hashCode() : 0);
        result = 31 * result + getWeight();
        return result;
    }

    @Override
    public int compareTo(ProductCategory o) {
        if (o == null) {
            return 1;
        }
        return categoryName.compareTo(o.getCategoryName());
    }

    @Override
    public String toString() {
        return "ProductCategoryImpl{" +
                "id=" + id +
                ", level=" + level +
                ", categoryName=" + categoryName +
                ", createdBy=" + createdBy +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", attributeTemplate=" + attributeTemplate +
                '}';
    }
}