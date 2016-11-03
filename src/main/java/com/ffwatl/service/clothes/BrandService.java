package com.ffwatl.service.clothes;


import com.ffwatl.admin.entities.items.brand.Brand;

import java.util.List;

public interface BrandService {

    Brand findById(long id);

    void save(Brand brand);

    void save(List<Brand> list);

    Brand findByName(String name);

    List<Brand> findAll();

    void removeByName(String name);

    void removeById(long id);

    List<Brand> findAllUsed();
}
