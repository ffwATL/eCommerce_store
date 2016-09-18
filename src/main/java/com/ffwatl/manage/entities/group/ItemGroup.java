package com.ffwatl.manage.entities.group;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ffwatl.manage.entities.i18n.I18n;
import com.ffwatl.manage.entities.items.CommonCategory;
import com.ffwatl.manage.entities.users.User;

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
@Table(name = "itemsGroup")
@Inheritance( strategy = InheritanceType.JOINED )
public class ItemGroup implements Comparable<ItemGroup>, Serializable{

    /**
     * ItemGroup identifier.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    /**
     * ItemGroup's depth level value.
     */
    private int level;

    /**
     * Common attribute for ItemGroup objects.
     */
    private CommonCategory cat;

    /**
     * Group name object that contains different language translations.
     */
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private I18n groupName;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private User createdBy;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JsonIgnore
    private ItemGroup parent;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
    private List<ItemGroup> child = new LinkedList<>();

    @Column(length = 2048)
    private String description;


    public User getCreatedBy() {
        return createdBy;
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

    public ItemGroup getParent() {
        return parent;
    }

    public List<ItemGroup> getChild() {
        return child;
    }

    public I18n getGroupName() {
        return groupName;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCat(CommonCategory cat) {
        this.cat = cat;
    }

    public ItemGroup setGroupName(I18n groupName) {
        this.groupName = groupName;
        return this;
    }

    public ItemGroup setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemGroup setParent(ItemGroup parent) {
        this.parent = parent;
        return this;
    }

    public ItemGroup setChild(List<ItemGroup> child) {
        this.child = child;
        return this;
    }

    public ItemGroup setLevel(int level) {
        this.level = level;
        return this;
    }


    @Override
    public String toString() {
        return "ItemGroup{" +
                "id=" + id +
                ", level=" + level +
                ", cat=" + cat +
                ", groupName=" + groupName +
                ", createdBy=" + createdBy +
                ", parent=" + (parent != null ? parent.groupName : null) +
                ", child=" + child +
                '}';
    }

    @Override
    public int compareTo(ItemGroup o) {
        if(o == null) return 1;
        return groupName.compareTo(o.groupName);
    }
}