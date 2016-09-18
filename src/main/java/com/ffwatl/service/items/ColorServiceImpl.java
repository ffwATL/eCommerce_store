package com.ffwatl.service.items;


import com.ffwatl.dao.items.ColorDao;
import com.ffwatl.manage.entities.items.color.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService{

    @Autowired
    private ColorDao colorDao;

    @Override
    public Color findById(long id) {
        return colorDao.findById(id);
    }

    @Override
    @Transactional
    public void save(Color c) {
        if(c == null) throw new IllegalArgumentException("Entity Color can't be null");
        colorDao.save(c);
    }

    @Override
    @Transactional
    public void save(List<Color> list) {
        if(list == null || list.size() < 1) throw new IllegalArgumentException("Entity Color can't be null");
        list.forEach(this::save);
    }

    @Override
    public List<Color> findAll() {
        return colorDao.findAll();
    }

    @Override
    public List<Color> findAllUsed() {
        return colorDao.findAllUsed();
    }
}