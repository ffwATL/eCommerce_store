package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.Category;
import com.ffwatl.admin.catalog.domain.CategoryImpl;
import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.dto.CategoryDTO;
import com.ffwatl.admin.catalog.dao.CategoryDao;
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
    private CategoryDao categoryDao;

    /**
     * Returns CategoryImpl object from DB by given id value;
     * @param id CategoryImpl identifier value;
     * @return CategoryImpl object.
     */
    @Override
    public Category findById(long id) {
        return categoryDao.findById(id);
    }

    /**
     * Persisting given CategoryImpl object into DB. Throws IllegalArgumentException
     * if given parameter is null;
     * @param itemGroup CategoryImpl object to be persist into DB;
     * @throws IllegalArgumentException
     */
    @Override
    @Transactional
    public void save(Category itemGroup) {
        if(itemGroup == null) throw new IllegalArgumentException();
        categoryDao.save((CategoryImpl) itemGroup);
    }

    /**
     * Persisting given list of CategoryImpl objects into DB. Throws IllegalArgumentException
     * if given parameter is null or if list size < 1;
     * @param list list of CategoryImpl objects to persist in DB.
     * @throws IllegalArgumentException
     */
    @Override
    @Transactional
    public void save(List<? extends Category> list) {
        if(list == null || list.size() < 1) throw new IllegalArgumentException("Bad parameter: list");
        list.forEach(this::save);
    }

    /**
     * Returns CategoryImpl object by its name.
     * @param name name of the CategoryImpl object to search;
     * @return CategoryImpl object if it present in the DB or null if it not.
     */
    @Override
    public Category findByName(@NotNull String name) {
        List<CategoryImpl> list = categoryDao.findByName(name);
        if(list.size() < 1) return null;
        return new CategoryDTO(list.get(0));
    }

    /**
     * Returns CategoryImpl object from the DB by its depth level and name;
     * @param lvl depth level value;
     * @param name name of the CategoryImpl object to search;
     * @return CategoryImpl object if it present in the DB or null if it not.
     */
    @Override
    public Category findByLvlAndByNameFetchCollection(int lvl, @NotNull String name) {
        return new CategoryDTO(categoryDao.findByLvlAndNameFetched(lvl, name), Category.FETCHED);
    }



    @Override
    public List<Category> findByCatNoChildren(@NotNull final CommonCategory cat) {
        return itemGroupList2DTOList(categoryDao.findByCat(cat), Category.NO_CHILD);
    }

    /**
     * Returns list of CategoryImpl object from the DB by its depth level.
     * The main thing is every object in that list WILL NOT CONTAIN ANY CHILD.
     * Child field will be always null;
     * @param lvl depth level value;
     * @return list of CategoryImpl objects without children.
     */
    @Override
    public List<Category> findByLvlLazyWithoutChild(int lvl) {
        return itemGroupList2DTOList(categoryDao.findByLvl(lvl), Category.NO_CHILD);
    }

    @Override
    public List<Category> findGenderGroup(){
        return itemGroupList2DTOList(categoryDao.findByLvl(2), Category.NO_CHILD);
    }

    /**
     * Returns list of CategoryImpl object from the DB by its depth level.
     * Result list will always contain all the children. A lot of additional
     * requests to the DB will execute. So if you don't need all the children
     * you should use findByLvlLazyWithoutChild method;
     * @param lvl depth level value;
     * @return ist of CategoryImpl objects with children
     */
    @Override
    public List<Category> findByLvlEager(int lvl) {
        return itemGroupList2DTOList(categoryDao.findByLvlFetchedChild(lvl), Category.FETCHED);
    }

    @Override
    public List<Category> findAllUsed() {
        return itemGroupList2DTOList(categoryDao.findAllUsed(), Category.NO_CHILD);
    }

    private List<Category> itemGroupList2DTOList(List<CategoryImpl> list, boolean fetched){
        List<Category> result = new ArrayList<>();
        for(Category i: list){
            result.add(new CategoryDTO(i, fetched));
        }
        return result;
    }

}