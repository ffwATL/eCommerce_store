package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.dao.SizeDao;
import com.ffwatl.admin.catalog.domain.ProductAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SizeServiceImpl implements SizeService{

    @Autowired
    private SizeDao sizeDao;

    @Override
    public ProductAttribute findById(long id) {
        return sizeDao.findById(id);
    }

    @Override
    @Transactional
    public void removeById(long... id) {
        for(long i: id){
            sizeDao.removeById(i);
        }
    }

}