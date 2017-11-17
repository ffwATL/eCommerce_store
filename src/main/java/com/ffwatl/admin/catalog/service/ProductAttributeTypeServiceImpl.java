package com.ffwatl.admin.catalog.service;

import com.ffwatl.admin.catalog.dao.EuroSizeDao;
import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.ProductAttributeType;
import com.ffwatl.common.persistence.FetchMode;
import com.ffwatl.common.service.ConverterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ProductAttributeTypeServiceImpl extends ConverterDTO<ProductAttributeType> implements ProductAttributeTypeService {

    @Autowired
    private EuroSizeDao euroSizeDao;


    @Override
    public ProductAttributeType findById(long id) {
        return euroSizeDao.findById(id);
    }

    @Override
    @Transactional
    public void save(ProductAttributeType eu) {
        euroSizeDao.save((ProductAttributeType)eu);
    }

    @Override
    @Transactional
    public List<ProductAttributeType> findByCat(CommonCategory cat){
        return transformList(euroSizeDao.findByCat(cat), DTO_OBJECT, FetchMode.LAZY);
    }

    @Override
    public List<ProductAttributeType> findAllUsed() {
        List<ProductAttributeType> list = transformList(euroSizeDao.findAllUsed(), DTO_OBJECT, FetchMode.LAZY);
        Collections.sort(list);
        return list;
    }

    @Override
    @Transactional
    public void save(List<ProductAttributeType> list) {
        if(list == null || list.size() < 1) return;
        list.forEach(this::save);
    }

    @Override
    public void removeSizeById(long id) {
        if(id > 0) euroSizeDao.removeSizeById(id);
    }

    @Override
    public List<ProductAttributeType> findAll() {
        return transformList(euroSizeDao.findAll(), DTO_OBJECT, FetchMode.LAZY);
    }

    @Override
    public ProductAttributeType transformDTO2Entity(ProductAttributeType old, FetchMode fetchMode) {
        return /*(ProductAttributeType) new ProductAttributeType()
                .setId(old.getId())
                .setCat(old.getCat())*/ null;
    }

    @Override
    public ProductAttributeType transformEntity2DTO(ProductAttributeType old, FetchMode fetchMode) {
        return /*new ProductAttributeTypeDTO()
                .setId(old.getId())
                .setName(old.getName())
                .setCat(old.getCat())*/ null;
    }
}