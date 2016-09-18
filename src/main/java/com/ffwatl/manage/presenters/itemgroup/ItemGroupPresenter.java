package com.ffwatl.manage.presenters.itemgroup;


import com.ffwatl.manage.entities.i18n.I18n;
import com.ffwatl.manage.entities.items.CommonCategory;

/**
 * Wrapper class for ItemGroup.class. The main purpose of this instance is
 * to remove tree structure, that ItemGroup has. This class was designed for
 * ClothesFilterPresenter class to represent refine category filter as list of
 * the entities but not as a tree.
 */
public class ItemGroupPresenter {

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

    public ItemGroupPresenter setId(long id) {
        this.id = id;
        return this;
    }

    public ItemGroupPresenter setGroupName(I18n groupName) {
        this.groupName = groupName;
        return this;
    }

    public ItemGroupPresenter setLvl(int lvl) {
        this.lvl = lvl;
        return this;
    }

    public ItemGroupPresenter setCat(CommonCategory cat) {
        this.cat = cat;
        return this;
    }

    @Override
    public String toString() {
        return "ItemGroupPresenter{" +
                "id=" + id +
                ", groupName=" + groupName +
                ", lvl=" + lvl +
                ", cat=" + cat +
                '}';
    }
}