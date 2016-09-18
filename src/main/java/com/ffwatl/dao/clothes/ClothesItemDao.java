package com.ffwatl.dao.clothes;


import com.ffwatl.manage.entities.items.clothes.ClothesItem;

public interface ClothesItemDao {

    ClothesItem findById(long id);

    void save(ClothesItem item);

    void remove(ClothesItem item);
}