package com.ffwatl.dao.clothes;


import com.ffwatl.domain.items.brand.Brand;

import java.util.List;

public interface BrandDao {

    Brand findById(long id);

    void save(Brand brand);

    List<Brand> findByName(String name);

    List<Brand> findAll();

    void remove(Brand brand);

}