package com.ffwatl.admin.catalog.domain.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ffwatl.admin.catalog.domain.ProductAttributeTemplate;
import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class for CategoryImpl.class. The main purpose of this instance is
 * to remove tree structure, that CategoryImpl has. This class was designed for
 * FilterProductClothes class to represent refine productCategory filter as list of
 * the entities but not as a tree.
 */
public class ProductCategoryDTO implements ProductCategory {

    /**
     * Identifier of CategoryImpl entity in database.
     */
    private long id;

    private int weight;

    /**
     * ProductCategory's depth level value.
     */
    private int level;

    @JsonDeserialize(as=UserDTO.class)
    private User createdBy;

    private ProductCategory parent;

    @JsonDeserialize(contentAs=ProductCategoryDTO.class)
    private List<ProductCategory> child = new ArrayList<>();

    /**
     * Group name object that contains different language translations.
     */
    private I18n categoryName;

    private String description;

    @JsonDeserialize(contentAs = ProductAttributeTemplateDTO.class)
    private ProductAttributeTemplate attributeTemplate;

    public ProductCategoryDTO() {
    }

    public ProductCategoryDTO(ProductCategory i, boolean fetched) {
        setId(i.getId());
        setCategoryName(i.getCategoryName());
        setCreatedBy(i.getCreatedBy());
        setDescription(i.getDescription());
        setLevel(i.getLevel());
        setWeight(i.getWeight());
        setProductAttributeTemplate(i.getProductAttributeTemplate());

        if(fetched && i.getChild() != null){
            for(ProductCategory c : i.getChild()) {
                child.add(new ProductCategoryDTO(c, true));
            }
        }
    }


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
    public int compareTo(ProductCategory o) {
        if (o == null) {
            return 1;
        }
        return categoryName.compareTo(o.getCategoryName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductCategoryDTO that = (ProductCategoryDTO) o;

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
    public String toString() {
        return "ProductCategoryDTO{" +
                "id=" + id +
                ", weight=" + weight +
                ", level=" + level +
                ", createdBy=" + createdBy +
                ", categoryName=" + categoryName +
                ", description='" + description + '\'' +
                ", attributeTemplate=" + attributeTemplate +
                '}';
    }
}