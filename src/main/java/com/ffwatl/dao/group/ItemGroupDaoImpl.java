package com.ffwatl.dao.group;


import com.ffwatl.manage.entities.group.ItemGroup;
import com.ffwatl.manage.entities.items.CommonCategory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemGroupDaoImpl implements ItemGroupDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public ItemGroup findById(long id) {
        return em.find(ItemGroup.class, id);
    }

    @Override
    public List<ItemGroup> findByName(String name) {
        return em.createQuery("SELECT i FROM ItemGroup i WHERE " +
                "i.groupName.locale_en=:name " +
                "OR i.groupName.locale_ru=:name " +
                "OR i.groupName.locale_ua=:name", ItemGroup.class)
                .setParameter("name",name)
                .getResultList();
    }

    @Override
    public void save(ItemGroup itemGroup) {
        em.persist(itemGroup);
    }

    @Override
    public void remove(ItemGroup itemGroup) {
        em.remove(itemGroup);
    }

    @Override
    public List<ItemGroup> findByLvlAndByName(int lvl, String name) {
        return em.createQuery("SELECT i FROM ItemGroup i WHERE i.level=:level AND (i.groupName.locale_en=:name"+                
                " OR i.groupName.locale_ru=:name" +
                " OR i.groupName.locale_ua=:name)", ItemGroup.class)
                .setParameter("level", lvl)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public List<ItemGroup> findByCat(CommonCategory cat) {
        return em.createQuery("SELECT i FROM ItemGroup i WHERE i.cat=:cat", ItemGroup.class)
                .setParameter("cat", cat)
                .getResultList();
    }

    @Override
    public List<ItemGroup> findByLvl(int lvl) {
        return em.createQuery("SELECT i FROM ItemGroup i WHERE i.level=:level", ItemGroup.class)
                .setParameter("level", lvl)
                .getResultList();
    }

    @Override
    public List<ItemGroup> findAllUsed() {
        return em.createQuery("SELECT DISTINCT i.itemGroup FROM Item i", ItemGroup.class).getResultList();
    }

    @Override
    public ItemGroup findByLvlAndNameFetched(int lvl, String name){
        return em.createQuery("SELECT i FROM ItemGroup i LEFT JOIN FETCH i.child WHERE i.level=:lvl " +
                "AND i.groupName.locale_en=:name", ItemGroup.class)
                .setParameter("lvl",lvl)
                .setParameter("name",name)
                .getSingleResult();
    }

}