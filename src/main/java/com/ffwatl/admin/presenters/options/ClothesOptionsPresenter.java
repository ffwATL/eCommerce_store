package com.ffwatl.admin.presenters.options;


import com.ffwatl.admin.entities.group.IGroup;
import com.ffwatl.admin.entities.items.brand.Brand;
import com.ffwatl.admin.entities.items.color.Color;

import java.util.List;

/**
 * This class uses for a response
 */
public class ClothesOptionsPresenter {

    private List<Brand> brandList;
    private List<Color> colorList;
    private IGroup itemGroup;
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

    public IGroup getItemGroup() {
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

    public void setItemGroup(IGroup itemGroup) {
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