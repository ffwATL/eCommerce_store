package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.ProductClothes;
import com.ffwatl.admin.catalog.domain.presenter.ClothesItemPresenter;

import java.util.List;
import java.util.Optional;

public interface ClothesItemService {

    ProductClothes findById(long id);
    Optional<List<ClothesItemPresenter>> findAll();
    void removeById(long id);
    long save (Optional<ClothesItemPresenter> optional, String email);
    void save(Optional<List<ClothesItemPresenter>> optionals);
}