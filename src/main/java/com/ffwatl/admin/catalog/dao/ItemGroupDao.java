package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.CategoryImpl;
import com.ffwatl.admin.catalog.domain.CommonCategory;

import java.util.List;

public interface ItemGroupDao {

    CategoryImpl findById(long id);

    List<CategoryImpl> findByName(String name);

    void save(CategoryImpl itemGroup);

    void remove(CategoryImpl itemGroup);

    List<CategoryImpl> findByLvlAndByName(int lvl, String name);

    List<CategoryImpl> findByCat(CommonCategory cat);

    List<CategoryImpl> findByLvl(int lvl);

    List<CategoryImpl> findAllUsed();
    CategoryImpl findByLvlAndNameFetched(int lvl, String name);
    List<CategoryImpl> findByLvlFetchedChild(int lvl);
}