package com.ffwatl.domain.filter.ui;


import com.ffwatl.domain.group.ItemGroup;
import com.ffwatl.domain.group.wrap.GroupWrapper;
import com.ffwatl.domain.items.CommonCategory;
import com.ffwatl.domain.items.brand.Brand;
import com.ffwatl.domain.items.clothes.size.EuroSize;
import com.ffwatl.domain.items.color.Color;

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
public class ClothesFilterOptions implements Serializable {

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
    private List<GroupWrapper> allCategories = new ArrayList<>();

    /**
     * List of 'gender' item groups.
     */
    private List<GroupWrapper> gender = new ArrayList<>();

    /**
     * List of all colors.
     */
    private List<Color> colors;


    public List<Color> getColors() {
        return colors;
    }

    public List<GroupWrapper> getGender() {
        return gender;
    }

    public List<GroupWrapper> getAllCategories() {
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

    public void setItemGroup(ItemGroup itemGroup) {
        resolveItemGroup(itemGroup, this.allCategories, this.gender);
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    /**
     * Method for extracting two types of categories. First type is 'all categories' type,
     * so this method returns GroupWrapper object. You shouldn't do anything with it.
     * Second type is 'gender' type of categories (Men, Women, etc.). All the results
     * stored in your input params:  'all' and 'gender' lists.
     * @param itemGroup ItemGroup object that contain List<ItemGroup> of children;
     * @param all List<GroupWrapper> object that will contain 'all categories' GroupWrapper objects;
     * @param gender List<GroupWrapper> object that will contain 'gender' GroupWrapper objects;
     * @return GroupWrapper object. You should ignore this value from outside of this method.
     */
    private GroupWrapper resolveItemGroup(ItemGroup itemGroup, List<GroupWrapper> all, List<GroupWrapper> gender){
        if(itemGroup.getLevel() == 2){
            gender.add(new GroupWrapper()
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
        return new GroupWrapper()
                .setCat(itemGroup.getCat())
                .setGroupName(itemGroup.getGroupName())
                .setId(itemGroup.getId())
                .setLvl(itemGroup.getLevel());
    }

    @Override
    public String toString() {
        return "ClothesFilterOptions{" +
                "brandList=" + brandList +
                ", size=" + size +
                ", allCategories=" + allCategories +
                '}';
    }
}