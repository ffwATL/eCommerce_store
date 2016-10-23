package com.ffwatl.dao.clothes;


import com.ffwatl.manage.entities.items.clothes.ClothesItem;

import java.util.List;

public interface ClothesItemDao {

    ClothesItem findById(long id);

    List<ClothesItem> findAll();

    void save(ClothesItem item);

    void remove(ClothesItem item);
}