package com.ffwatl.domain.update.express_info;


import com.ffwatl.domain.group.wrap.GroupWrapper;
import com.ffwatl.domain.items.color.Color;

import java.io.Serializable;
import java.util.List;

/**
 * Class for handling information that sends back to UI for Express Edit feature;
 */
public class ItemsExpressInfo implements Serializable{

    private List<GroupWrapper> itemGroup;

    private List<Color> color;

    public List<GroupWrapper> getItemGroup() {
        return itemGroup;
    }

    public List<Color> getColor() {
        return color;
    }

    public ItemsExpressInfo setItemGroup(List<GroupWrapper> itemGroup) {
        this.itemGroup = itemGroup;
        return this;
    }

    public ItemsExpressInfo setColor(List<Color> color) {
        this.color = color;
        return this;
    }
}