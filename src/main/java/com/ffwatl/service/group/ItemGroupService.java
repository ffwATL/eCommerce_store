package com.ffwatl.service.group;


import com.ffwatl.admin.dto.ItemGroupDto;
import com.ffwatl.admin.entities.group.ItemGroup;
import com.ffwatl.admin.presenters.itemgroup.ItemGroupPresenter;
import com.ffwatl.admin.entities.items.CommonCategory;

import java.util.List;

public interface ItemGroupService {

    ItemGroup findById(long id);

    void save(ItemGroup itemGroup);

    void save(List<? extends ItemGroup> list);

    ItemGroup findByName(String name);

    ItemGroupDto findByLvlAndByNameFetchCollection(int lvl, String name);

    List<ItemGroupPresenter> findByCatNoChildren(CommonCategory cat);

    List<ItemGroup> findByLvlLazyWithoutChild(int lvl);

    List<ItemGroup> findByLvlEager(int lvl);

    List<ItemGroup> findAllUsed();

    List<ItemGroupPresenter> findGenderGroup();

    List<ItemGroupPresenter> findAllUsedWrapper();

}