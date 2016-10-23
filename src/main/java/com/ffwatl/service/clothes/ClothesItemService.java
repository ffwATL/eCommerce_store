package com.ffwatl.service.clothes;


import java.util.List;
import java.util.Optional;

import com.ffwatl.manage.entities.items.clothes.ClothesItem;
import com.ffwatl.manage.presenters.items.ClothesItemPresenter;

public interface ClothesItemService {

    ClothesItem findById(long id);
    Optional<List<ClothesItemPresenter>> findAll();
    void removeById(long id);
    void save (Optional<ClothesItemPresenter> optional, String email);
    void save(Optional<List<ClothesItemPresenter>> optionals);
}