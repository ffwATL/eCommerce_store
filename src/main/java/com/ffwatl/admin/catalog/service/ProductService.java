package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.ProductImpl;
import com.ffwatl.admin.catalog.domain.presenter.ProductUpdateImpl;
import com.ffwatl.admin.catalog.domain.presenter.ItemUpdatePresenter;
import com.ffwatl.common.persistence.FetchMode;

import java.util.List;

public interface ProductService {

    ProductImpl findById(long id);
    Product findById(long id, FetchMode fetchMode);

    List<ProductImpl> findAll();
    List<Product> findAll(FetchMode fetchMode);

    void save(ProductImpl item);
    Product save(Product product);

    void remove(Product item);

    void changeItemStatus(ProductImpl item);
    void updateSingleItem(ItemUpdatePresenter update);
    void updateItems(ItemUpdatePresenter update);
    ProductUpdateImpl findItemPresenterById(long id);
}