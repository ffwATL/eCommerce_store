package com.ffwatl.manage.presenters.items.update;


import com.ffwatl.manage.entities.items.Item;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

/**
 * Presenter class that used for sending update information about Item from front-end part
 * of application. Have two purposes:
 *      1) Update single item. In this case all the information about Item should be in the
 *          field 'item'; FIXME: Separate this functionality to another class;
 *      2) Update more than one item. In this case all the Item's ID should be in the field identifiers,
 *          and all the Item's data to be changed should be in the Map 'options';
 */
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

    @Override
    public String toString() {
        return "ItemUpdatePresenter{" +
                "identifiers=" + Arrays.toString(identifiers) +
                ", options=" + options +
                ", item=" + item +
                '}';
    }
}