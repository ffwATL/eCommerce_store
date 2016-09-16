package com.ffwatl.service.group;


import com.ffwatl.dao.group.ItemGroupDao;
import com.ffwatl.domain.group.ItemGroup;

import com.ffwatl.domain.group.wrap.GroupWrapper;
import com.ffwatl.domain.items.CommonCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ItemGroupService.
 */
@Service
@Transactional(readOnly = true)
public class ItemGroupServiceImpl implements ItemGroupService{

    @Autowired
    private ItemGroupDao itemGroupDao;

    /**
     * Returns ItemGroup object from DB by given id value;
     * @param id ItemGroup identifier value;
     * @return ItemGroup object.
     */
    @Override
    public ItemGroup findById(long id) {
        return itemGroupDao.findById(id);
    }

    /**
     * Persisting given ItemGroup object into DB. Throws IllegalArgumentException
     * if given parameter is null;
     * @param itemGroup ItemGroup object to be persist into DB;
     * @throws IllegalArgumentException
     */
    @Override
    @Transactional
    public void save(ItemGroup itemGroup) {
        if(itemGroup == null) throw new IllegalArgumentException();
        itemGroupDao.save(itemGroup);
    }

    /**
     * Persisting given list of ItemGroup objects into DB. Throws IllegalArgumentException
     * if given parameter is null or if list size < 1;
     * @param list list of ItemGroup objects to persist in DB.
     * @throws IllegalArgumentException
     */
    @Override
    @Transactional
    public void save(List<? extends ItemGroup> list) {
        if(list == null || list.size() < 1) throw new IllegalArgumentException("Bad parameter: list");
        list.forEach(this::save);
    }

    /**
     * Returns ItemGroup object by its name.
     * @param name name of the ItemGroup object to search;
     * @return ItemGroup object if it present in the DB or null if it not.
     */
    @Override
    public ItemGroup findByName(String name) {
        if(name == null) return null;
        List<ItemGroup> list = itemGroupDao.findByName(name);
        if(list.size() < 1) return null;
        return list.get(0);
    }

    /**
     * Returns ItemGroup object from the DB by its depth level and name;
     * @param lvl depth level value;
     * @param name name of the ItemGroup object to search;
     * @return ItemGroup object if it present in the DB or null if it not.
     */
    @Override
    public ItemGroup findByLvlAndByNameNoLazy(int lvl, String name) {
        List<ItemGroup> list = itemGroupDao.findByLvlAndByName(lvl, name);
        System.out.println(list);
        if(list.size() > 0) return list.get(0);
        return null;
    }

    @Override
    public List<GroupWrapper> findByCatNoChildren(CommonCategory cat) {
        List<ItemGroup> groupList = itemGroupDao.findByCat(cat);
        List<GroupWrapper> result = new ArrayList<>();
        for(ItemGroup i: groupList){
            result.add(new GroupWrapper()
                    .setCat(cat)
                    .setId(i.getId())
                    .setGroupName(i.getGroupName())
                    .setLvl(i.getLevel()));
        }
        return result;
    }

    /**
     * Returns list of ItemGroup object from the DB by its depth level.
     * The main thing is every object in that list WILL NOT CONTAIN ANY CHILD.
     * Child field will be always null;
     * @param lvl depth level value;
     * @return list of ItemGroup objects without children.
     */
    @Override
    public List<ItemGroup> findByLvlLazyWithoutChild(int lvl) {
        List<ItemGroup> result = itemGroupDao.findByLvl(lvl);
        for(ItemGroup itemGroup: result){
            itemGroup.setChild(null);
        }
        return result;
    }

    /**
     * Returns list of ItemGroup object from the DB by its depth level.
     * Result list will always contain all the children. A lot of additional
     * requests to the DB will execute. So if you don't need all the children
     * you should use findByLvlLazyWithoutChild method;
     * @param lvl depth level value;
     * @return ist of ItemGroup objects with children
     */
    @Override
    public List<ItemGroup> findByLvlEager(int lvl) {
        List<ItemGroup> result = itemGroupDao.findByLvl(lvl);
        System.out.println(result);
        return result;
    }

}