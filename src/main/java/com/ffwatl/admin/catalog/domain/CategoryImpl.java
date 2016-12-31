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
    private int level;

    /**
     * Common attribute for CategoryImpl objects.
     */
    private CommonCategory cat;

    /**
     * Group name object that contains different language translations.
     */
    @Embedded
    private I18n groupName;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, targetEntity = UserImpl.class)
    private User createdBy;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = CategoryImpl.class)
    private List<Category> child = new LinkedList<>();

    @Column(length = 2048)
    private String description;

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
    public String toString() {
        return "CategoryImpl{" +
                "id=" + id +
                ", level=" + level +
                ", cat=" + cat +
                ", groupName=" + groupName +
                ", createdBy=" + createdBy +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", child: "+child+
                '}';
    }

    @Override
    public int compareTo(Category o) {
        if(o == null) return 1;
        return groupName.compareTo(o.getGroupName());
    }
}