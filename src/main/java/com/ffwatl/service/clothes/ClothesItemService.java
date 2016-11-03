package com.ffwatl.service.clothes;


import com.ffwatl.admin.entities.items.clothes.ClothesItem;
import com.ffwatl.admin.presenters.items.ClothesItemPresenter;

import java.util.List;
import java.util.Optional;

public interface ClothesItemService {

    ClothesItem findById(long id);
    Optional<List<ClothesItemPresenter>> findAll();
    void removeById(long id);
    long save (Optional<ClothesItemPresenter> optional, String email);
    void save(Optional<List<ClothesItemPresenter>> optionals);
}