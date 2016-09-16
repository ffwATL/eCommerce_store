package com.ffwatl.service.group;


import com.ffwatl.domain.group.ItemGroup;
import com.ffwatl.domain.group.wrap.GroupWrapper;
import com.ffwatl.domain.items.CommonCategory;

import java.util.List;

public interface ItemGroupService {

    ItemGroup findById(long id);

    void save(ItemGroup itemGroup);

    void save(List<? extends ItemGroup> list);

    ItemGroup findByName(String name);

    ItemGroup findByLvlAndByNameNoLazy(int lvl, String name);

    List<GroupWrapper> findByCatNoChildren(CommonCategory cat);

    List<ItemGroup> findByLvlLazyWithoutChild(int lvl);

    List<ItemGroup> findByLvlEager(int lvl);

}