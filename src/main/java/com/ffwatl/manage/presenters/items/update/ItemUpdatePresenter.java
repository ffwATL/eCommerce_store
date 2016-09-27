package com.ffwatl.manage.presenters.items.update;


import com.ffwatl.manage.entities.items.Item;

import java.io.Serializable;
import java.util.Map;

public class ItemUpdatePresenter implements Serializable{

    private long [] identifiers;

    private Map<String, String> options;

    private Item item;

    public Item getItem() {
        return item;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public long[] getIdentifiers() {
        return identifiers;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setIdentifiers(long[] identifiers) {
        this.identifiers = identifiers;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }
}