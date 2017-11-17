package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.dao.ProductAttributeDao;
import com.ffwatl.admin.catalog.domain.ProductAttribute;
import com.ffwatl.common.persistence.FetchMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service("product_attribute_service")
@Transactional(readOnly = true)
public class ProductAttributeServiceImpl implements ProductAttributeService {

    private static final Logger LOGGER = LogManager.getLogger();


    @Autowired
    private ProductAttributeDao productAttributeDao;

    @Override
    public ProductAttribute findById(long id) {
        LOGGER.trace("findById --> id={}", id);

        if (id < 1) {
            LOGGER.error("findById --> wrong id='{}' is given", id);
            throw new IllegalArgumentException("wrong 'ProductAttribute' id is given: " + id);
        }

        ProductAttribute productAttribute = productAttributeDao.findById(id);
        LOGGER.trace("findById --> {}", productAttribute);
        return productAttribute;
    }

    @Override
    public ProductAttribute findById(long id, FetchMode fetchMode) {
        LOGGER.trace("findById --> id={}, fetchMode={}", id, fetchMode);

        if(id < 1) {
            LOGGER.error("findById --> wrong id='{}' is given", id);
            throw new IllegalArgumentException("wrong 'ProductAttribute' id is given: " + id);
        }

        ProductAttribute productAttribute = productAttributeDao.findById(id, fetchMode);
        LOGGER.trace("findById --> fetchMode={}, {}", fetchMode, productAttribute);
        return productAttribute;
    }

    @Override
    @Transactional
    public void removeById(long ... id) {
        for (long i: id) {
            LOGGER.debug("removeById --> id={}", i);
            productAttributeDao.removeById(i);
        }
    }

}