package com.ffwatl.service.clothes;


import com.ffwatl.manage.entities.items.clothes.ClothesItem;

import java.io.IOException;

public interface ClothesItemService {

    ClothesItem findById(long id);

    void removeById(long id);

    void save(ClothesItem item, String email) throws IOException;
}
