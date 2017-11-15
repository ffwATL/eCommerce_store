package com.ffwatl.admin.catalog.domain.response;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.admin.catalog.domain.Color;
import com.ffwatl.admin.catalog.domain.dto.ProductCategoryDTO;
import com.ffwatl.admin.catalog.domain.dto.ColorDTO;

import java.io.Serializable;
import java.util.List;

/**
 * Class for handling information that sends back to UI for Express Edit feature;
 */
public class ItemsExpressInfoPresenter implements Serializable{

    @JsonDeserialize(as=ProductCategoryDTO.class)
    private List<ProductCategory> itemGroup;
    @JsonDeserialize(as=ColorDTO.class)
    private List<Color> color;

    public List<ProductCategory> getItemGroup() {
        return itemGroup;
    }

    public List<Color> getColor() {
        return color;
    }

    public ItemsExpressInfoPresenter setItemGroup(List<ProductCategory> itemGroup) {
        this.itemGroup = itemGroup;
        return this;
    }

    public ItemsExpressInfoPresenter setColor(List<Color> color) {
        this.color = color;
        return this;
    }
}