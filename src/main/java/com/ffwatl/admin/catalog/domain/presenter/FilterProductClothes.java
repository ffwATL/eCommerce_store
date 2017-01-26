package com.ffwatl.admin.catalog.domain.presenter;


import com.ffwatl.admin.catalog.domain.*;
import com.ffwatl.admin.catalog.domain.dto.CategoryDTO;

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
public class FilterProductClothes implements Serializable {

    /**
     * List of clothes brands.
     */
    private List<Brand> brandList;

    /**
     * List of clothes size.
     */
    private List<ProductAttributeType> size;

    /**
     * List of 'all' item groups.
     */
    private List<Category> allCategories = new ArrayList<>();

    private List<Category> usedCat = new ArrayList<>();

    /**
     * List of 'gender' item groups.
     */
    private List<Category> gender = new ArrayList<>();

    /**
     * List of all colors.
     */
    private List<Color> colors;

    public List<Category> getUsedCat() {
        return usedCat;
    }

    public List<Color> getColors() {
        return colors;
    }

    public List<Category> getGender() {
        return gender;
    }

    public List<Category> getAllCategories() {
        return allCategories;
    }

    public List<ProductAttributeType> getSize() {
        return size;
    }

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public void setSize(List<ProductAttributeType> size) {
        this.size = size;
    }

    public void setItemGroupAll(Category itemGroup) {
        resolveItemGroup(itemGroup, this.allCategories, this.gender);
    }

    public void setUsedCat(List<Category> usedCat) {
        this.usedCat = usedCat;
    }

    public void setGender(List<Category> gender) {
        this.gender = gender;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    /**
     * Method for extracting two types of categories. First type is 'all categories' type,
     * so this method returns CategoryDTO object. You shouldn't do anything with it.
     * Second type is 'gender' type of categories (Men, Women, etc.). All the results
     * stored in your input params:  'all' and 'gender' lists.
     * @param itemGroup CategoryImpl object that contain List<CategoryImpl> of children;
     * @param all List<CategoryDTO> object that will contain 'all categories' CategoryDTO objects;
     * @param gender List<CategoryDTO> object that will contain 'gender' CategoryDTO objects;
     * @return CategoryDTO object. You should ignore this value from outside of this method.
     */
    private Category resolveItemGroup(Category itemGroup, List<Category> all, List<Category> gender){
        if(itemGroup.getLevel() == 2){
            gender.add(new CategoryDTO(itemGroup));
        }
        if(itemGroup.getChild() != null && itemGroup.getChild().size() > 0){
            for(Category i: itemGroup.getChild()) {
                if(i.getCat() != CommonCategory.NONE) all.add(resolveItemGroup(i, all, gender));
                else resolveItemGroup(i, all, gender);
            }
        }
        return new CategoryDTO(itemGroup);
    }

    @Override
    public String toString() {
        return "FilterProductClothes{" +
                "brandList=" + brandList +
                ", size=" + size +
                ", allCategories=" + allCategories +
                '}';
    }
}