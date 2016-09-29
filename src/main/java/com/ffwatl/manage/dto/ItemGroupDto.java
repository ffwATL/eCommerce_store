package com.ffwatl.manage.dto;


import com.ffwatl.manage.entities.group.ItemGroup;
import com.ffwatl.manage.entities.i18n.I18n;
import com.ffwatl.manage.entities.items.CommonCategory;

import java.util.ArrayList;
import java.util.List;

public class ItemGroupDto {

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
    private List<ItemGroupDto> child;
    private String description;
    private int weight;


    public ItemGroupDto(ItemGroup itemGroup){
        this.id = itemGroup.getId();
        this.cat = itemGroup.getCat();
        this.description = itemGroup.getDescription();
        this.level = itemGroup.getLevel();
        this.weight = itemGroup.getWeight();
        this.groupName = itemGroup.getGroupName();
        if(itemGroup.getChild() != null){
            child = new ArrayList<>();
            for(ItemGroup i: itemGroup.getChild()){
                child.add(new ItemGroupDto(i));
            }
        }
    }

    public long getId() {
        return id;
    }

    public I18n getGroupName() {
        return groupName;
    }

    public int getLevel() {
        return level;
    }

    public CommonCategory getCat() {
        return cat;
    }

    public List<ItemGroupDto> getChild() {
        return child;
    }

    public String getDescription() {
        return description;
    }

    public int getWeight() {
        return weight;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setGroupName(I18n groupName) {
        this.groupName = groupName;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCat(CommonCategory cat) {
        this.cat = cat;
    }

    public void setChild(List<ItemGroupDto> child) {
        this.child = child;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ItemGroupDto{" +
                "id=" + id +
                ", groupName=" + groupName +
                ", level=" + level +
                ", cat=" + cat +
                ", child=" + child +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                '}';
    }
}