package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.Category;
import com.ffwatl.admin.catalog.domain.CategoryImpl;
import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.common.persistence.FetchMode;

import java.util.List;

public interface CategoryDao {

    CategoryImpl findById(long id);

    Category findById(long id, FetchMode fetchMode);

    List<CategoryImpl> findByName(String name);

    List<Category> findByName(String name, FetchMode fetchMode);

    Category save(Category itemGroup);

    void remove(Category itemGroup);

    List<CategoryImpl> findByLvlAndByName(int lvl, String name);

    List<CategoryImpl> findByCat(CommonCategory cat);

    List<Category> findByCommonCategory(CommonCategory cat, FetchMode fetchMode);

    List<CategoryImpl> findByLvl(int lvl);

    List<Category> findByLevel(int level, FetchMode fetchMode);

    List<Category> findAllUsed();
    List<Category> findAllInUse(FetchMode fetchMode);
    List<Category> findByLevelAndName(int level, String name, FetchMode fetchMode);

    CategoryImpl findByLvlAndNameFetched(int lvl, String name);
    List<CategoryImpl> findByLvlFetchedChild(int lvl);
}