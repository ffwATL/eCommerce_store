package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.dao.ProductCategoryDao;
import com.ffwatl.admin.catalog.domain.ProductCategory;
import com.ffwatl.admin.catalog.domain.ProductCategoryImpl;
import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.dto.ProductCategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ItemGroupService.
 */
@Service
@Transactional(readOnly = true)
public class ItemGroupServiceImpl implements ItemGroupService{

    @Autowired
    private ProductCategoryDao productCategoryDao;

    /**
     * Returns ProductCategoryImpl object from DB by given id value;
     * @param id ProductCategoryImpl identifier value;
     * @return ProductCategoryImpl object.
     */
    @Override
    public ProductCategory findById(long id) {
        return productCategoryDao.findById(id);
    }

    /**
     * Persisting given ProductCategoryImpl object into DB. Throws IllegalArgumentException
     * if given parameter is null;
     * @param productCategory ProductCategoryImpl object to be persist into DB;
     * @throws IllegalArgumentException
     */
    @Override
    @Transactional
    public void save(ProductCategory productCategory) {
        if(productCategory == null) {
            throw new IllegalArgumentException();
        }
        productCategoryDao.save(productCategory);
    }

    /**
     * Persisting given list of ProductCategoryImpl objects into DB. Throws IllegalArgumentException
     * if given parameter is null or if list size < 1;
     * @param list list of ProductCategoryImpl objects to persist in DB.
     * @throws IllegalArgumentException
     */
    @Override
    @Transactional
    public void save(List<? extends ProductCategory> list) {
        if(list == null || list.size() < 1) {
            throw new IllegalArgumentException("Bad parameter: list = " + list);
        }
        list.forEach(this::save);
    }

    /**
     * Returns ProductCategoryImpl object by its name.
     * @param name name of the ProductCategoryImpl object to search;
     * @return ProductCategoryImpl object if it present in the DB or null if it not.
     */
    @Override
    public ProductCategory findByName(@NotNull String name) {
        List<ProductCategoryImpl> list = productCategoryDao.findByName(name);
        if(list.size() < 1) return null;
        return new ProductCategoryDTO(list.get(0));
    }

    /**
     * Returns ProductCategoryImpl object from the DB by its depth level and name;
     * @param level depth level value;
     * @param name name of the ProductCategoryImpl object to search;
     * @return ProductCategoryImpl object if it present in the DB or null if it not.
     */
    @Override
    public ProductCategory findByLvlAndByNameFetchCollection(int level, @NotNull String name) {
        return new ProductCategoryDTO(productCategoryDao.findByLvlAndNameFetched(level, name), ProductCategory.FETCHED);
    }



    @Override
    @Deprecated
    public List<ProductCategory> findByCatNoChildren(@NotNull final CommonCategory cat) {
        return /*itemGroupList2DTOList(productCategoryDao.findByCat(cat), ProductCategory.NO_CHILD)*/ null;
    }

    /**
     * Returns list of ProductCategoryImpl object from the DB by its depth level.
     * The main thing is every object in that list WILL NOT CONTAIN ANY CHILD.
     * Child field will be always null;
     * @param lvl depth level value;
     * @return list of ProductCategoryImpl objects without children.
     */
    @Override
    public List<ProductCategory> findByLvlLazyWithoutChild(int lvl) {
        return /*itemGroupList2DTOList(productCategoryDao.findByLevel(lvl), ProductCategory.NO_CHILD)*/ null;
    }

    @Override
    @Deprecated
    public List<ProductCategory> findGenderGroup(){
        return /*itemGroupList2DTOList(productCategoryDao.findByLevel(2), ProductCategory.NO_CHILD);*/ null;
    }

    /**
     * Returns list of ProductCategoryImpl object from the DB by its depth level.
     * Result list will always contain all the children. A lot of additional
     * requests to the DB will execute. So if you don't need all the children
     * you should use findByLvlLazyWithoutChild method;
     * @param lvl depth level value;
     * @return ist of ProductCategoryImpl objects with children
     */
    @Override
    @Deprecated
    public List<ProductCategory> findByLvlEager(int lvl) {
        return null; /*itemGroupList2DTOList(productCategoryDao.findByLvlFetchedChild(lvl), ProductCategory.FETCHED);*/
    }

    @Override
    public List<ProductCategory> findAllUsed() {
        return itemGroupList2DTOList(productCategoryDao.findAllUsed(), ProductCategory.NO_CHILD);
    }

    private List<ProductCategory> itemGroupList2DTOList(List<ProductCategory> list, boolean fetched){
        List<ProductCategory> result = new ArrayList<>();
        for(ProductCategory i: list){
            result.add(new ProductCategoryDTO(i, fetched));
        }
        return result;
    }

}