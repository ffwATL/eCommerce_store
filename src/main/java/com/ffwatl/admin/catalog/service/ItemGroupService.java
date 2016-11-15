package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.Category;
import com.ffwatl.admin.catalog.domain.CommonCategory;

import java.util.List;

public interface ItemGroupService {

    Category findById(long id);

    void save(Category itemGroup);

    void save(List<? extends Category> list);

    Category findByName(String name);

    Category findByLvlAndByNameFetchCollection(int lvl, String name);

    List<Category> findByCatNoChildren(CommonCategory cat);

    List<Category> findByLvlLazyWithoutChild(int lvl);

    List<Category> findByLvlEager(int lvl);

    List<Category> findAllUsed();

    List<Category> findGenderGroup();

}