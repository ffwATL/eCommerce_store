package com.ffwatl.service.group;


import com.ffwatl.admin.entities.group.IGroup;
import com.ffwatl.admin.entities.group.ItemGroup;
import com.ffwatl.admin.entities.items.CommonCategory;
import com.ffwatl.admin.presenters.itemgroup.ItemGroupPresenter;
import com.ffwatl.dao.group.ItemGroupDao;
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
    private ItemGroupDao itemGroupDao;

    /**
     * Returns ItemGroup object from DB by given id value;
     * @param id ItemGroup identifier value;
     * @return ItemGroup object.
     */
    @Override
    public IGroup findById(long id) {
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
    public void save(IGroup itemGroup) {
        if(itemGroup == null) throw new IllegalArgumentException();
        itemGroupDao.save((ItemGroup) itemGroup);
    }

    /**
     * Persisting given list of ItemGroup objects into DB. Throws IllegalArgumentException
     * if given parameter is null or if list size < 1;
     * @param list list of ItemGroup objects to persist in DB.
     * @throws IllegalArgumentException
     */
    @Override
    @Transactional
    public void save(List<? extends IGroup> list) {
        if(list == null || list.size() < 1) throw new IllegalArgumentException("Bad parameter: list");
        list.forEach(this::save);
    }

    /**
     * Returns ItemGroup object by its name.
     * @param name name of the ItemGroup object to search;
     * @return ItemGroup object if it present in the DB or null if it not.
     */
    @Override
    public IGroup findByName(@NotNull String name) {
        List<ItemGroup> list = itemGroupDao.findByName(name);
        if(list.size() < 1) return null;
        return new ItemGroupPresenter(list.get(0));
    }

    /**
     * Returns ItemGroup object from the DB by its depth level and name;
     * @param lvl depth level value;
     * @param name name of the ItemGroup object to search;
     * @return ItemGroup object if it present in the DB or null if it not.
     */
    @Override
    public IGroup findByLvlAndByNameFetchCollection(int lvl, @NotNull String name) {
        return new ItemGroupPresenter(itemGroupDao.findByLvlAndNameFetched(lvl, name), IGroup.FETCHED);
    }



    @Override
    public List<IGroup> findByCatNoChildren(@NotNull final CommonCategory cat) {
        return itemGroupList2DTOList(itemGroupDao.findByCat(cat), IGroup.NO_CHILD);
    }

    /**
     * Returns list of ItemGroup object from the DB by its depth level.
     * The main thing is every object in that list WILL NOT CONTAIN ANY CHILD.
     * Child field will be always null;
     * @param lvl depth level value;
     * @return list of ItemGroup objects without children.
     */
    @Override
    public List<IGroup> findByLvlLazyWithoutChild(int lvl) {
        return itemGroupList2DTOList(itemGroupDao.findByLvl(lvl), IGroup.NO_CHILD);
    }

    @Override
    public List<IGroup> findGenderGroup(){
        return itemGroupList2DTOList(itemGroupDao.findByLvl(2), IGroup.NO_CHILD);
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
    public List<IGroup> findByLvlEager(int lvl) {
        return itemGroupList2DTOList(itemGroupDao.findByLvlFetchedChild(lvl), IGroup.FETCHED);
    }

    @Override
    public List<IGroup> findAllUsed() {
        return itemGroupList2DTOList(itemGroupDao.findAllUsed(), IGroup.NO_CHILD);
    }

    private List<IGroup> itemGroupList2DTOList(List<ItemGroup> list, boolean fetched){
        List<IGroup> result = new ArrayList<>();
        for(IGroup i: list){
            result.add(new ItemGroupPresenter(i, fetched));
        }
        return result;
    }

}