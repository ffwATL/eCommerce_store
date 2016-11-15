package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.BrandImpl;

import java.util.List;

public interface BrandDao {

    BrandImpl findById(long id);

    void save(BrandImpl brand);

    List<BrandImpl> findByName(String name);

    List<BrandImpl> findAll();

    void remove(BrandImpl brand);

    public List<BrandImpl> findAllUsed();

}