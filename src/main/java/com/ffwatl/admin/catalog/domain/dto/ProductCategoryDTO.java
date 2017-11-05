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

    /**
     * Group name object that contains different language translations.
     */
    private I18n groupName;

    /**
     * ProductCategory's depth level value.
     */
    private int level;


    private String description;

    private int weight;

    @JsonDeserialize(contentAs=ProductCategoryDTO.class)

    private List<ProductCategory> child = new ArrayList<>();

    @JsonDeserialize(as=UserDTO.class)
    private User createdBy;

    public ProductCategoryDTO(){}

    public ProductCategoryDTO(ProductCategory i){
        this(i, ProductCategory.NO_CHILD);
    }

    public ProductCategoryDTO(ProductCategory i, boolean fetched){
        this.id = i.getId();
        this.description = i.getDescription();
        this.level = i.getLevel();
        this.weight = i.getWeight();
        this.groupName = i.getGroupName();
        this.createdBy = i.getCreatedBy();
        this.weight = i.getWeight();

        if(fetched && i.getChild() != null){
            for(ProductCategory c : i.getChild()){
                child.add(new ProductCategoryDTO(c, ProductCategory.FETCHED));
            }
        }
    }


    public long getId() {
        return id;
    }

    public I18n getGroupName() {
        return groupName;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public ProductAttributeTemplate getProductAttributeTemplate() {
        return null;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public User getCreatedBy() {
        return this.createdBy;
    }

    @Override
    public List<ProductCategory> getChild() {
        return this.child;
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
        return this;
    }

    @Override
    public int compareTo(ProductCategory o) {
        if(o == null) return 1;
        return groupName.compareTo(o.getGroupName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductCategoryDTO that = (ProductCategoryDTO) o;

        if (getId() != that.getId()) return false;
        if (getLevel() != that.getLevel()) return false;
        if (getWeight() != that.getWeight()) return false;
        if (getGroupName() != null ? !getGroupName().equals(that.getGroupName()) : that.getGroupName() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(that.getDescription()) : that.getDescription() != null)
            return false;
        if (getChild() != null ? !getChild().equals(that.getChild()) : that.getChild() != null) return false;
        return !(getCreatedBy() != null ? !getCreatedBy().equals(that.getCreatedBy()) : that.getCreatedBy() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getGroupName() != null ? getGroupName().hashCode() : 0);
        result = 31 * result + getLevel();
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + getWeight();
        result = 31 * result + (getChild() != null ? getChild().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductCategoryDTO{" +
                "id=" + id +
                ", groupName=" + groupName +
                ", level=" + level +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", child=" + child +
                ", createdBy=" + createdBy +
                '}';
    }
}