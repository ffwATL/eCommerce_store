package com.ffwatl.admin.presenters.items.update;


import com.ffwatl.admin.entities.group.IGroup;
import com.ffwatl.admin.entities.items.color.Color;

import java.io.Serializable;
import java.util.List;

/**
 * Class for handling information that sends back to UI for Express Edit feature;
 */
public class ItemsExpressInfoPresenter implements Serializable{

    private List<IGroup> itemGroup;

    private List<Color> color;

    public List<IGroup> getItemGroup() {
        return itemGroup;
    }

    public List<Color> getColor() {
        return color;
    }

    public ItemsExpressInfoPresenter setItemGroup(List<IGroup> itemGroup) {
        this.itemGroup = itemGroup;
        return this;
    }

    public ItemsExpressInfoPresenter setColor(List<Color> color) {
        this.color = color;
        return this;
    }
}