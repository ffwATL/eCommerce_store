package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.dao.ColorDao;
import com.ffwatl.admin.catalog.domain.Color;
import com.ffwatl.admin.catalog.domain.ColorImpl;
import com.ffwatl.admin.catalog.domain.dto.ColorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ColorServiceImpl extends ConverterDTO<Color> implements ColorService{

    @Autowired
    private ColorDao colorDao;

    @Override
    public Color findById(long id) {
        return colorDao.findById(id);
    }

    @Override
    @Transactional
    public void save(Color c) {
        if(c == null) throw new IllegalArgumentException("Entity ColorImpl can't be null");
        colorDao.save(c);
    }

    @Override
    @Transactional
    public void save(List<Color> list) {
        if(list == null || list.size() < 1) throw new IllegalArgumentException("Entity ColorImpl can't be null");
        list.forEach(this::save);
    }

    @Override
    public List<Color> findAll() {
        return transformList(colorDao.findAll(), DTO_OBJECT);
    }

    @Override
    public List<Color> findAllUsed() {
        return transformList(colorDao.findAllUsed(), DTO_OBJECT);
    }


    @Override
    public ColorImpl transformDTO2Entity(Color old) {
        return (ColorImpl) new ColorImpl()
                .setId(old.getId())
                .setColor(old.getColor())
                .setHex(old.getHex());
    }

    @Override
    public Color transformEntity2DTO(Color old) {
        return new ColorDTO()
                .setId(old.getId())
                .setColor(old.getColor())
                .setHex(old.getHex());
    }
}