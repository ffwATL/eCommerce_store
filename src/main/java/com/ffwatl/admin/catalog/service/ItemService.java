package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.ProductDefault;
import com.ffwatl.admin.catalog.domain.presenter.ProductUpdateImpl;
import com.ffwatl.admin.catalog.domain.presenter.ItemUpdatePresenter;

import java.util.List;

public interface ItemService {

    ProductDefault findById(long id);
    List<ProductDefault> findAll();
    void save(ProductDefault item);
    void remove(ProductDefault item);
    void changeItemStatus(ProductDefault item);
    void updateSingleItem(ItemUpdatePresenter update);
    void updateItems(ItemUpdatePresenter update);
    ProductUpdateImpl findItemPresenterById(long id);
}