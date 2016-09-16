package com.ffwatl.service.clothes;


import com.ffwatl.domain.items.clothes.ClothesItem;

public interface ClothesItemService {

    ClothesItem findById(long id);

    void removeById(long id);

    void save(ClothesItem item);
}
