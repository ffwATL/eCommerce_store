package com.ffwatl.dao.clothes;


import com.ffwatl.domain.items.clothes.ClothesItem;

public interface ClothesItemDao {

    ClothesItem findById(long id);

    void save(ClothesItem item);

    void remove(ClothesItem item);
}