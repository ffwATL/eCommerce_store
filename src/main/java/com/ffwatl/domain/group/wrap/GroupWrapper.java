package com.ffwatl.domain.group.wrap;


import com.ffwatl.domain.i18n.I18n;
import com.ffwatl.domain.items.CommonCategory;

/**
 * Wrapper class for ItemGroup.class. The main purpose of this instance is
 * to remove tree structure, that ItemGroup has. This class was designed for
 * ClothesFilterOptions class to represent refine category filter as list of
 * the entities but not as a tree.
 */
public class GroupWrapper {

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
    private int lvl;

    /**
     * Common attribute for ItemGroup objects.
     */
    private CommonCategory cat;

    public long getId() {
        return id;
    }

    public I18n getGroupName() {
        return groupName;
    }

    public int getLvl() {
        return lvl;
    }

    public CommonCategory getCat() {
        return cat;
    }

    public GroupWrapper setId(long id) {
        this.id = id;
        return this;
    }

    public GroupWrapper setGroupName(I18n groupName) {
        this.groupName = groupName;
        return this;
    }

    public GroupWrapper setLvl(int lvl) {
        this.lvl = lvl;
        return this;
    }

    public GroupWrapper setCat(CommonCategory cat) {
        this.cat = cat;
        return this;
    }

    @Override
    public String toString() {
        return "GroupWrapper{" +
                "id=" + id +
                ", groupName=" + groupName +
                ", lvl=" + lvl +
                ", cat=" + cat +
                '}';
    }
}