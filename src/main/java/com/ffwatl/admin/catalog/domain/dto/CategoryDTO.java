package com.ffwatl.admin.catalog.domain.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ffwatl.admin.catalog.domain.Category;
import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class for CategoryImpl.class. The main purpose of this instance is
 * to remove tree structure, that CategoryImpl has. This class was designed for
 * FilterProductClothes class to represent refine category filter as list of
 * the entities but not as a tree.
 */
public class CategoryDTO implements Category {

    /**
     * Identifier of CategoryImpl entity in database.
     */
    private long id;

    /**
     * Group name object that contains different language translations.
     */
    private I18n groupName;

    /**
     * Category's depth level value.
     */
    private int level;

    /**
     * Common attribute for Category objects.
     */
    private CommonCategory cat;

    private String description;

    private int weight;

    @JsonDeserialize(contentAs=CategoryDTO.class)

    private List<Category> child = new ArrayList<>();

    @JsonDeserialize(as=UserDTO.class)
    private User createdBy;

    public CategoryDTO(){}

    public CategoryDTO(Category i){
        this(i, Category.NO_CHILD);
    }

    public CategoryDTO(Category i, boolean fetched){
        this.id = i.getId();
        this.cat = i.getCat();
        this.description = i.getDescription();
        this.level = i.getLevel();
        this.weight = i.getWeight();
        this.groupName = i.getGroupName();
        this.createdBy = i.getCreatedBy();
        this.weight = i.getWeight();
        if(fetched && i.getChild() != null){
            for(Category c : i.getChild()){
                child.add(new CategoryDTO(c, Category.FETCHED));
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
    public CommonCategory getCat() {
        return this.cat;
    }

    @Override
    public List<Category> getChild() {
        return this.child;
    }
    @Override
    public Category setId(long id) {
        this.id = id;
        return this;
    }
    @Override
    public Category setGroupName(I18n groupName) {
        this.groupName = groupName;
        return this;
    }

    @Override
    public Category setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    @Override
    public Category setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Category setChild(List<Category> child) {
        this.child = child;
        return this;
    }
    @Override
    public Category setLevel(int level) {
        this.level = level;
        return this;
    }

    @Override
    public Category setWeight(int weight) {
        this.weight = weight;
        return this;
    }
    @Override
    public Category setCat(CommonCategory cat) {
        this.cat = cat;
        return this;
    }

    @Override
    public int compareTo(Category o) {
        if(o == null) return 1;
        return groupName.compareTo(o.getGroupName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryDTO that = (CategoryDTO) o;

        if (getId() != that.getId()) return false;
        if (getLevel() != that.getLevel()) return false;
        if (getWeight() != that.getWeight()) return false;
        if (getGroupName() != null ? !getGroupName().equals(that.getGroupName()) : that.getGroupName() != null)
            return false;
        if (getCat() != that.getCat()) return false;
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
        result = 31 * result + (getCat() != null ? getCat().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + getWeight();
        result = 31 * result + (getChild() != null ? getChild().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "id=" + id +
                ", groupName=" + groupName +
                ", level=" + level +
                ", cat=" + cat +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", child=" + child +
                ", createdBy=" + createdBy +
                '}';
    }
}