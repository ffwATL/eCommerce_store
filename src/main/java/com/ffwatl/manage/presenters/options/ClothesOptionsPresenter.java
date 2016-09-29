package com.ffwatl.manage.presenters.options;


import com.ffwatl.manage.dto.ItemGroupDto;
import com.ffwatl.manage.entities.items.brand.Brand;
import com.ffwatl.manage.entities.items.color.Color;

import java.util.List;

/**
 * This class uses for a response
 */
public class ClothesOptionsPresenter {

    private List<Brand> brandList;
    private List<Color> colorList;
    private ItemGroupDto itemGroup;
    private String brandImgUrl;

    public String getBrandImgUrl() {
        return brandImgUrl;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public List<Color> getColorList() {
        return colorList;
    }

    public ItemGroupDto getItemGroup() {
        return itemGroup;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public void setBrandImgUrl(String brandImgUrl) {
        this.brandImgUrl = brandImgUrl;
    }

    public void setColorList(List<Color> colorList) {
        this.colorList = colorList;
    }

    public void setItemGroup(ItemGroupDto itemGroup) {
        this.itemGroup = itemGroup;
    }


    @Override
    public String toString() {
        return "ClothesOptionsPresenter{" +
                "brandList=" + brandList +
                ", colorList=" + colorList +
                ", itemGroup=" + itemGroup +
                ", brandImgUrl='" + brandImgUrl + '\'' +
                '}';
    }
}