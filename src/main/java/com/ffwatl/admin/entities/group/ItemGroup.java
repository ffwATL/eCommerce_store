package com.ffwatl.admin.entities.group;

import com.ffwatl.admin.entities.i18n.I18n;
import com.ffwatl.admin.entities.items.CommonCategory;
import com.ffwatl.admin.entities.users.IUser;
import com.ffwatl.admin.entities.users.User;

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
public class ItemGroup implements Comparable<ItemGroup>, Serializable, IGroup{

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
    @Embedded
    private I18n groupName;

    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, targetEntity = User.class)
    private IUser createdBy;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = ItemGroup.class)
    private List<IGroup> child = new LinkedList<>();

    @Column(length = 2048)
    private String description;

    private int weight;


    public IUser getCreatedBy() {
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

    public List<IGroup> getChild() {
        return child;
    }

    public I18n getGroupName() {
        return groupName;
    }

    public String getDescription() {
        return description;
    }

    public IGroup setId(long id) {
        this.id = id;
        return this;
    }

    public IGroup setCat(CommonCategory cat) {
        this.cat = cat;
        return this;
    }

    public IGroup setGroupName(I18n groupName) {
        this.groupName = groupName;
        return this;
    }

    public IGroup setCreatedBy(IUser createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public IGroup setDescription(String description) {
        this.description = description;
        return this;
    }

    public IGroup setChild(List<IGroup> child) {
        this.child = child;
        return this;
    }

    public IGroup setLevel(int level) {
        this.level = level;
        return this;
    }

    public IGroup setWeight(int weight) {
        this.weight = weight;
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
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", child: "+child+
                '}';
    }

    @Override
    public int compareTo(ItemGroup o) {
        if(o == null) return 1;
        return groupName.compareTo(o.groupName);
    }
}