package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.dao.ProductAttributeDao;
import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.common.persistence.FetchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("product_attribute_service")
@Transactional(readOnly = true)
public class ProductAttributeServiceImpl implements ProductAttributeService {

    @Autowired
    private ProductAttributeDao productAttributeDao;

    @Override
    public ProductAttribute findById(long id) {
        return productAttributeDao.findById(id);
    }

    @Override
    public ProductAttribute findById(long id, FetchMode fetchMode) {
        return productAttributeDao.findById(id, fetchMode);
    }

    @Override
    @Transactional
    public void removeById(long... id) {
        for(long i: id){
            productAttributeDao.removeById(i);
        }
    }

}