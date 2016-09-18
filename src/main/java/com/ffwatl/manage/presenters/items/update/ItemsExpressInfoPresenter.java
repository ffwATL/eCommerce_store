package com.ffwatl.manage.presenters.items.update;


import com.ffwatl.manage.presenters.itemgroup.ItemGroupPresenter;
import com.ffwatl.manage.entities.items.color.Color;

import java.io.Serializable;
import java.util.List;

/**
 * Class for handling information that sends back to UI for Express Edit feature;
 */
public class ItemsExpressInfoPresenter implements Serializable{

    private List<ItemGroupPresenter> itemGroup;

    private List<Color> color;

    public List<ItemGroupPresenter> getItemGroup() {
        return itemGroup;
    }

    public List<Color> getColor() {
        return color;
    }

    public ItemsExpressInfoPresenter setItemGroup(List<ItemGroupPresenter> itemGroup) {
        this.itemGroup = itemGroup;
        return this;
    }

    public ItemsExpressInfoPresenter setColor(List<Color> color) {
        this.color = color;
        return this;
    }
}