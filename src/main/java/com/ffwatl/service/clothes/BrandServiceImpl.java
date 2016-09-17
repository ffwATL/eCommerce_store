package com.ffwatl.service.clothes;


import com.ffwatl.dao.clothes.BrandDao;
import com.ffwatl.domain.items.brand.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;

    @Override
    public Brand findById(long id) {
        return brandDao.findById(id);
    }

    @Override
    @Transactional
    public void save(Brand brand) {
        if(brand == null) throw new IllegalArgumentException("Brand can't be null");
        brandDao.save(brand);
    }

    @Override
    @Transactional
    public void save(List<Brand> list) {
        if(list == null || list.size() < 1) throw new IllegalArgumentException("Brand list: " + list);
        list.forEach(brandDao::save);
    }

    @Override
    public Brand findByName(String name) {
        List<Brand> list = brandDao.findByName(name);
        return list.size() < 1 ? null: list.get(0);
    }

    @Override
    public List<Brand> findAll() {
        return brandDao.findAll();
    }

    @Override
    @Transactional
    public void removeByName(String name) {
        if(name == null) throw new IllegalArgumentException("Name can't be null");
        Brand b = findByName(name);
        if(b == null) throw new IllegalArgumentException("Such element not present in DB");
        brandDao.remove(b);
    }

    @Override
    public List<Brand> findAllUsed() {
        return brandDao.findAllUsed();
    }
}