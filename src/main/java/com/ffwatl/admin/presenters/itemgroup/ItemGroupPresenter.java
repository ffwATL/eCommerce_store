package com.ffwatl.admin.presenters.itemgroup;


import com.ffwatl.admin.entities.group.IGroup;
import com.ffwatl.admin.entities.i18n.I18n;
import com.ffwatl.admin.entities.items.CommonCategory;
import com.ffwatl.admin.entities.users.IUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class for ItemGroup.class. The main purpose of this instance is
 * to remove tree structure, that ItemGroup has. This class was designed for
 * ClothesFilterPresenter class to represent refine category filter as list of
 * the entities but not as a tree.
 */
public class ItemGroupPresenter implements IGroup{

    /**
     * Identifier of ItemGroup entity in database.
     */
    private long id;

    /**
     * Group name object that contains different language translations.
     */
    private I18n groupName;

    /**
     * ItemGroup's depth level value.
     */
    private int level;

    /**
     * Common attribute for ItemGroup objects.
     */
    private CommonCategory cat;
    private List<IGroup> child = new ArrayList<>();
    private String description;
    private int weight;
    private IUser createdBy;

    public ItemGroupPresenter(){}

    public ItemGroupPresenter(IGroup i){
        this(i, IGroup.NO_CHILD);
    }

    public ItemGroupPresenter(IGroup i, boolean fetched){
        this.id = i.getId();
        this.cat = i.getCat();
        this.description = i.getDescription();
        this.level = i.getLevel();
        this.weight = i.getWeight();
        this.groupName = i.getGroupName();
        this.createdBy = i.getCreatedBy();
        this.weight = i.getWeight();
        if(fetched && i.getChild() != null){
            for(IGroup c : i.getChild()){
                child.add(new ItemGroupPresenter(c, IGroup.FETCHED));
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
    public IUser getCreatedBy() {
        return this.createdBy;
    }
    @Override
    public CommonCategory getCat() {
        return this.cat;
    }

    @Override
    public List<IGroup> getChild() {
        return this.child;
    }
    @Override
    public IGroup setId(long id) {
        this.id = id;
        return this;
    }
    @Override
    public IGroup setGroupName(I18n groupName) {
        this.groupName = groupName;
        return this;
    }

    @Override
    public IGroup setCreatedBy(IUser createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    @Override
    public IGroup setDescription(String description) {
        this.description = description;
        return this;
    }

    @Override
    public IGroup setChild(List<IGroup> child) {
        this.child = child;
        return this;
    }
    @Override
    public IGroup setLevel(int level) {
        this.level = level;
        return this;
    }

    @Override
    public IGroup setWeight(int weight) {
        this.weight = weight;
        return this;
    }
    @Override
    public IGroup setCat(CommonCategory cat) {
        this.cat = cat;
        return this;
    }

    @Override
    public String toString() {
        return "ItemGroupPresenter{" +
                "id=" + id +
                ", groupName=" + groupName +
                ", level=" + level +
                ", cat=" + cat +
                '}';
    }
}