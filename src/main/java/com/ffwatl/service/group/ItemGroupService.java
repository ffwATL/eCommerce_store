package com.ffwatl.service.group;


import com.ffwatl.admin.entities.group.IGroup;
import com.ffwatl.admin.entities.items.CommonCategory;

import java.util.List;

public interface ItemGroupService {

    IGroup findById(long id);

    void save(IGroup itemGroup);

    void save(List<? extends IGroup> list);

    IGroup findByName(String name);

    IGroup findByLvlAndByNameFetchCollection(int lvl, String name);

    List<IGroup> findByCatNoChildren(CommonCategory cat);

    List<IGroup> findByLvlLazyWithoutChild(int lvl);

    List<IGroup> findByLvlEager(int lvl);

    List<IGroup> findAllUsed();

    List<IGroup> findGenderGroup();

}