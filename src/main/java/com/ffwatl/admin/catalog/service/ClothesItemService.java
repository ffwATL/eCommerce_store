package com.ffwatl.admin.catalog.service;



import com.ffwatl.admin.catalog.domain.ProductImpl;

import java.util.List;
import java.util.Optional;

public interface ClothesItemService {

    ProductImpl findById(long id);
    Optional<List<ProductImpl>> findAll();
    void removeById(long id);
    long save (Optional<ClothesItemPresenter> optional, String email);
    void save(Optional<List<ClothesItemPresenter>> optionals);
}