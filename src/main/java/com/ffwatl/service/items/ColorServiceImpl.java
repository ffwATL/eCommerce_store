package com.ffwatl.service.items;


import com.ffwatl.dao.items.ColorRepository;
import com.ffwatl.domain.items.color.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService{

    @Autowired
    private ColorRepository colorRepository;

    @Override
    public Color findById(long id) {
        return colorRepository.findOne(id);
    }

    @Override
    @Transactional
    public void save(Color c) {
        if(c == null) throw new IllegalArgumentException("Entity Color can't be null");
        colorRepository.save(c);
    }

    @Override
    @Transactional
    public void save(List<Color> list) {
        if(list == null || list.size() < 1) throw new IllegalArgumentException("Entity Color can't be null");
        colorRepository.save(list);
    }

    @Override
    public List<Color> findAll() {
        return colorRepository.findAll();
    }
}