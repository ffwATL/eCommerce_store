package com.ffwatl.admin.catalog.domain;

import com.ffwatl.admin.i18n.domain.I18n;
import com.ffwatl.admin.user.domain.User;
import com.ffwatl.admin.user.domain.UserImpl;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ffw_ATL
 * This entity class is for handling groups of items information. Main goal is to
 * group items by their common identity. This table is static, all the data uploads
 * only once. But if needed you can manually add new group through console.
 */
@Entity
@Table(name = "items_group")
public class CategoryImpl implements Serializable, Category {

    /**
     * CategoryImpl identifier.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    /**
     * CategoryImpl's depth level value.
     */
    @Column(name = "category_level")
    private int level;

    /**
     * Common attribute for CategoryImpl objects.
     */
    @Column(name = "common_category")
    private CommonCategory cat;

    /**
     * Group name object that contains different language translations.
     */
    @Embedded
    private I18n groupName;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, targetEntity = UserImpl.class)
    @JoinColumn(name = "user_id")
    private User createdBy;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = CategoryImpl.class)
    @JoinColumn(name = "child_id")
    private List<Category> child = new LinkedList<>();

    @Column(name="cat_description", length = 2048)
    private String description;

    @Column(name = "avg_weight")
    private int weight;


    public User getCreatedBy() {
        return createdBy;
    }

    public int getWeight() {
        return weight;
    }

    public long getId() {
        return id;
    }

    public CommonCategory getCat() {
        return cat;
    }

    public int getLevel() {
        return level;
    }

    public List<Category> getChild() {
        return child;
    }

    public I18n getGroupName() {
        return groupName;
    }

    public String getDescription() {
        return description;
    }

    public Category setId(long id) {
        this.id = id;
        return this;
    }

    public Category setCat(CommonCategory cat) {
        this.cat = cat;
        return this;
    }

    public Category setGroupName(I18n groupName) {
        this.groupName = groupName;
        return this;
    }

    public Category setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Category setDescription(String description) {
        this.description = description;
        return this;
    }

    public Category setChild(List<Category> child) {
        this.child = child;
        return this;
    }

    public Category setLevel(int level) {
        this.level = level;
        return this;
    }

    public Category setWeight(int weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryImpl category = (CategoryImpl) o;

        if (getId() != category.getId()) return false;
        if (getLevel() != category.getLevel()) return false;
        if (getWeight() != category.getWeight()) return false;
        if (getCat() != category.getCat()) return false;
        if (getGroupName() != null ? !getGroupName().equals(category.getGroupName()) : category.getGroupName() != null)
            return false;
        if (getCreatedBy() != null ? !getCreatedBy().equals(category.getCreatedBy()) : category.getCreatedBy() != null)
            return false;
        if (getChild() != null ? !getChild().equals(category.getChild()) : category.getChild() != null) return false;
        return !(getDescription() != null ? !getDescription().equals(category.getDescription()) : category.getDescription() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + getLevel();
        result = 31 * result + (getCat() != null ? getCat().hashCode() : 0);
        result = 31 * result + (getGroupName() != null ? getGroupName().hashCode() : 0);
        result = 31 * result + (getCreatedBy() != null ? getCreatedBy().hashCode() : 0);
        result = 31 * result + (getChild() != null ? getChild().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + getWeight();
        return result;
    }

    @Override
    public String toString() {
        return "CategoryImpl{" +
                "id=" + id +
                ", level=" + level +
                ", cat=" + cat +
                ", groupName=" + groupName +
                ", createdBy=" + createdBy +
                ", child=" + child +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Category o) {
        if(o == null) return 1;
        return groupName.compareTo(o.getGroupName());
    }
}