package com.ffwatl.service.items;

import com.ffwatl.dao.clothes.EuroSizeDao;
import com.ffwatl.manage.entities.items.CommonCategory;
import com.ffwatl.manage.entities.items.clothes.size.EuroSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class EuroSizeServiceImpl implements EuroSizeService {

    @Autowired
    private EuroSizeDao euroSizeDao;


    @Override
    public EuroSize findById(long id) {
        return euroSizeDao.findById(id);
    }

    @Override
    @Transactional
    public void save(EuroSize eu) {
        euroSizeDao.save(eu);
    }

    @Override
    @Transactional
    public List<EuroSize> findByCat(CommonCategory cat){
        return euroSizeDao.findByCat(cat);
    }

    @Override
    public List<EuroSize> findAllUsed() {
        List<EuroSize> list = euroSizeDao.findAllUsed();
        Collections.sort(list);
        return list;
    }

    @Override
    @Transactional
    public void save(List<EuroSize> list) {
        if(list == null || list.size() < 1) return;
        list.forEach(this::save);
    }

    @Override
    public void removeSizeById(long id) {
        if(id > 0) euroSizeDao.removeSizeById(id);
    }

    @Override
    public List<EuroSize> findAll() {
        return euroSizeDao.findAll();
    }

}