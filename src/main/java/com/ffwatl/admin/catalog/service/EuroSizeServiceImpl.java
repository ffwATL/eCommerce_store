package com.ffwatl.admin.catalog.service;

import com.ffwatl.admin.catalog.dao.EuroSizeDao;
import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.EuroSize;
import com.ffwatl.admin.catalog.domain.EuroSizeImpl;
import com.ffwatl.admin.catalog.domain.dto.EuroSizeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class EuroSizeServiceImpl extends ConverterDTO<EuroSize> implements EuroSizeService {

    @Autowired
    private EuroSizeDao euroSizeDao;


    @Override
    public EuroSizeImpl findById(long id) {
        return euroSizeDao.findById(id);
    }

    @Override
    @Transactional
    public void save(EuroSize eu) {
        euroSizeDao.save((EuroSizeImpl)eu);
    }

    @Override
    @Transactional
    public List<EuroSize> findByCat(CommonCategory cat){
        return transformList(euroSizeDao.findByCat(cat), DTO_OBJECT);
    }

    @Override
    public List<EuroSize> findAllUsed() {
        List<EuroSize> list = transformList(euroSizeDao.findAllUsed(), DTO_OBJECT);
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
        return transformList(euroSizeDao.findAll(), DTO_OBJECT);
    }

    @Override
    public EuroSizeImpl transformDTO2Entity(EuroSize old) {
        return (EuroSizeImpl) new EuroSizeImpl()
                .setId(old.getId())
                .setCat(old.getCat());
    }

    @Override
    public EuroSize transformEntity2DTO(EuroSize old) {
        return new EuroSizeDTO()
                .setId(old.getId())
                .setName(old.getName())
                .setCat(old.getCat());
    }
}