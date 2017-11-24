package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.Product;
import com.ffwatl.admin.catalog.domain.dto.response.AccessMode;
import com.ffwatl.common.persistence.FetchMode;

import java.util.List;

public interface ProductService {

    Product findById(long id, FetchMode fetchMode, AccessMode accessMode);

    List<Product> findAll(FetchMode fetchMode, AccessMode accessMode);

    Product save(Product product);

    void remove(Product item);

    void removeById(long id);

    void changeItemStatus(Product productRequest);

    void processSingleProductRequest(Product productRequest);

    void processProductRequestInBulk(List<Product> productRequestList);
}