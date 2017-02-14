package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.dao.BrandDao;
import com.ffwatl.admin.catalog.domain.Brand;
import com.ffwatl.admin.catalog.domain.BrandImpl;
import com.ffwatl.admin.catalog.domain.dto.BrandDTO;
import com.ffwatl.common.service.ConverterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandServiceImpl extends ConverterDTO<Brand> implements BrandService {

    @Autowired
    private BrandDao brandDao;

    @Override
    public BrandImpl findById(long id) {
        return brandDao.findById(id);
    }

    @Override
    @Transactional
    public void save(BrandImpl brand) {
        if(brand == null) throw new IllegalArgumentException("BrandImpl can't be null");
        if(findByName(brand.getName()) != null) throw new IllegalArgumentException("BrandImpl with same name is already exist");
        brandDao.save(brand);
    }

    @Override
    @Transactional
    public void save(List<BrandImpl> list) {
        if(list == null || list.size() < 1) throw new IllegalArgumentException("BrandImpl list: " + list);
        list.forEach(brandDao::save);
    }

    @Override
    public BrandImpl findByName(String name) {
        List<BrandImpl> list = brandDao.findByName(name);
        return list.size() < 1 ? null: list.get(0);
    }

    @Override
    public List<Brand> findAll() {
        return transformList(brandDao.findAll(), DTO_OBJECT);
    }

    @Override
    @Transactional
    public void removeByName(String name) {
        if(name == null) throw new IllegalArgumentException("Name can't be null");
        BrandImpl b = findByName(name);
        if(b == null) throw new IllegalArgumentException("Such element not present in DB");
        brandDao.remove(b);
    }

    @Override
    @Transactional
    public void removeById(long id) {
        brandDao.remove(findById(id));
    }

    @Override
    public List<Brand> findAllUsed() {
        return transformList(brandDao.findAllUsed(), DTO_OBJECT);
    }

    @Override
    public Brand transformDTO2Entity(Brand old) {
        return new BrandImpl()
                .setId(old.getId())
                .setName(old.getName())
                .setDescription(old.getDescription());
    }

    @Override
    public Brand transformEntity2DTO(Brand brandImpl){
        return new BrandDTO()
                .setId(brandImpl.getId())
                .setDescription(brandImpl.getDescription())
                .setName(brandImpl.getName());
    }
}