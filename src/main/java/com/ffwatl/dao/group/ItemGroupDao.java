package com.ffwatl.dao.group;


import com.ffwatl.domain.group.ItemGroup;
import com.ffwatl.domain.items.CommonCategory;

import java.util.List;

public interface ItemGroupDao {

    ItemGroup findById(long id);

    List<ItemGroup> findByName(String name);

    void save(ItemGroup itemGroup);

    void remove(ItemGroup itemGroup);

    List<ItemGroup> findByLvlAndByName(int lvl, String name);

    List<ItemGroup> findByCat(CommonCategory cat);

    List<ItemGroup> findByLvl(int lvl);
}
