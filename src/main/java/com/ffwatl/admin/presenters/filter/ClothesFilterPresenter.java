package com.ffwatl.admin.presenters.filter;


import com.ffwatl.admin.entities.group.ItemGroup;
import com.ffwatl.admin.presenters.itemgroup.ItemGroupPresenter;
import com.ffwatl.admin.entities.items.CommonCategory;
import com.ffwatl.admin.entities.items.brand.Brand;
import com.ffwatl.admin.entities.items.clothes.size.EuroSize;
import com.ffwatl.admin.entities.items.color.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for handling refine filtering information about "Clothes" category.
 * For now there are such filters available:
 *      - list of brands;
 *      - list of sizes;
 *      - list of 'all' item groups;
 *      - list of 'gender' item groups;
 *      - list of colors;
 */
public class ClothesFilterPresenter implements Serializable {

    /**
     * List of clothes brands.
     */
    private List<Brand> brandList;

    /**
     * List of clothes size.
     */
    private List<EuroSize> size;

    /**
     * List of 'all' item groups.
     */
    private List<ItemGroupPresenter> allCategories = new ArrayList<>();

    private List<ItemGroupPresenter> usedCat = new ArrayList<>();

    /**
     * List of 'gender' item groups.
     */
    private List<ItemGroupPresenter> gender = new ArrayList<>();

    /**
     * List of all colors.
     */
    private List<Color> colors;

    public List<ItemGroupPresenter> getUsedCat() {
        return usedCat;
    }

    public List<Color> getColors() {
        return colors;
    }

    public List<ItemGroupPresenter> getGender() {
        return gender;
    }

    public List<ItemGroupPresenter> getAllCategories() {
        return allCategories;
    }

    public List<EuroSize> getSize() {
        return size;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public void setSize(List<EuroSize> size) {
        this.size = size;
    }

    public void setItemGroupAll(ItemGroup itemGroup) {
        resolveItemGroup(itemGroup, this.allCategories, this.gender);
    }

    public void setUsedCat(List<ItemGroupPresenter> usedCat) {
        this.usedCat = usedCat;
    }

    public void setGender(List<ItemGroupPresenter> gender) {
        this.gender = gender;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    /**
     * Method for extracting two types of categories. First type is 'all categories' type,
     * so this method returns ItemGroupPresenter object. You shouldn't do anything with it.
     * Second type is 'gender' type of categories (Men, Women, etc.). All the results
     * stored in your input params:  'all' and 'gender' lists.
     * @param itemGroup ItemGroup object that contain List<ItemGroup> of children;
     * @param all List<ItemGroupPresenter> object that will contain 'all categories' ItemGroupPresenter objects;
     * @param gender List<ItemGroupPresenter> object that will contain 'gender' ItemGroupPresenter objects;
     * @return ItemGroupPresenter object. You should ignore this value from outside of this method.
     */
    private ItemGroupPresenter resolveItemGroup(ItemGroup itemGroup, List<ItemGroupPresenter> all, List<ItemGroupPresenter> gender){
        if(itemGroup.getLevel() == 2){
            gender.add(new ItemGroupPresenter()
                    .setGroupName(itemGroup.getGroupName())
                    .setId(itemGroup.getId())
                    .setLvl(itemGroup.getLevel())
                    .setCat(itemGroup.getCat()));
        }
        if(itemGroup.getChild() != null && itemGroup.getChild().size() > 0){
            for(ItemGroup i: itemGroup.getChild()) {
                if(i.getCat() != CommonCategory.NONE) all.add(resolveItemGroup(i, all, gender));
                else resolveItemGroup(i, all, gender);
            }
        }
        return new ItemGroupPresenter()
                .setCat(itemGroup.getCat())
                .setGroupName(itemGroup.getGroupName())
                .setId(itemGroup.getId())
                .setLvl(itemGroup.getLevel());
    }

    @Override
    public String toString() {
        return "ClothesFilterPresenter{" +
                "brandList=" + brandList +
                ", size=" + size +
                ", allCategories=" + allCategories +
                '}';
    }
}