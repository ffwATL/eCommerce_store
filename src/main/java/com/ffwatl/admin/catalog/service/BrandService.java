package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.Brand;
import com.ffwatl.admin.catalog.domain.BrandImpl;

import java.util.List;

public interface BrandService {

    BrandImpl findById(long id);

    void save(BrandImpl brand);

    void save(List<BrandImpl> list);

    BrandImpl findByName(String name);

    List<Brand> findAll();

    void removeByName(String name);

    void removeById(long id);

    List<Brand> findAllUsed();
}
