package com.ffwatl.admin.catalog.domain.dto.response;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.ProductImpl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

/**
 * Presenter class that used for sending update information about Product from front-end part
 * of application. Have two purposes:
 *      1) Update single item. In this case all the information about Product should be in the
 *          field 'item'; FIXME: Separate this functionality to another class;
 *      2) Update more than one item. In this case all the Product's ID should be in the field identifiers,
 *          and all the Product's data to be changed should be in the Map 'options';
 */
public class ItemUpdatePresenter implements Serializable{

    private long [] identifiers;

    private Map<String, String> options;
    @JsonDeserialize(as=ProductImpl.class)
    private Product item;

    public Product getItem() {
        return item;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public long[] getIdentifiers() {
        return identifiers;
    }

    public void setItem(Product item) {
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