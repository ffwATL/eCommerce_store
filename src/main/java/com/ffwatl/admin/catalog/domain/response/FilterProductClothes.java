package com.ffwatl.admin.catalog.domain.response;


import com.ffwatl.admin.catalog.domain.*;
import com.ffwatl.admin.catalog.domain.dto.ProductCategoryDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for handling refine filtering information about "Clothes" productCategory.
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
    private List<ProductCategory> allCategories = new ArrayList<>();

    private List<ProductCategory> usedCat = new ArrayList<>();

    /**
     * List of 'gender' item groups.
     */
    private List<ProductCategory> gender = new ArrayList<>();

    /**
     * List of all colors.
     */
    private List<Color> colors;

    public List<ProductCategory> getUsedCat() {
        return usedCat;
    }

    public List<Color> getColors() {
        return colors;
    }

    public List<ProductCategory> getGender() {
        return gender;
    }

    public List<ProductCategory> getAllCategories() {
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

    public void setItemGroupAll(ProductCategory itemGroup) {
        resolveItemGroup(itemGroup, this.allCategories, this.gender);
    }

    public void setUsedCat(List<ProductCategory> usedCat) {
        this.usedCat = usedCat;
    }

    public void setGender(List<ProductCategory> gender) {
        this.gender = gender;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }

    /**
     * Method for extracting two types of categories. First type is 'all categories' type,
     * so this method returns ProductCategoryDTO object. You shouldn't do anything with it.
     * Second type is 'gender' type of categories (Men, Women, etc.). All the results
     * stored in your input params:  'all' and 'gender' lists.
     * @param itemGroup ProductCategoryImpl object that contain List<ProductCategoryImpl> of children;
     * @param all List<ProductCategoryDTO> object that will contain 'all categories' ProductCategoryDTO objects;
     * @param gender List<ProductCategoryDTO> object that will contain 'gender' ProductCategoryDTO objects;
     * @return ProductCategoryDTO object. You should ignore this value from outside of this method.
     */
    private ProductCategory resolveItemGroup(ProductCategory itemGroup, List<ProductCategory> all, List<ProductCategory> gender){
        if(itemGroup.getLevel() == 2){
            gender.add(new ProductCategoryDTO(itemGroup));
        }
        if(itemGroup.getChild() != null && itemGroup.getChild().size() > 0){
            for(ProductCategory i: itemGroup.getChild()) {
                /*if(i.getCat() != CommonCategory.NONE) all.add(resolveItemGroup(i, all, gender));
                else resolveItemGroup(i, all, gender);*/ // FIXME: update pls!
            }
        }
        return new ProductCategoryDTO(itemGroup);
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