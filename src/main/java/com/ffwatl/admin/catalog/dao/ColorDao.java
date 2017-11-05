package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.Color;
import com.ffwatl.admin.catalog.domain.ColorImpl;

import java.util.List;


public interface ColorDao {

    Color findById(long id);

    void save(Color c);

    List<ColorImpl> findAll();

    List<ColorImpl> findAllUsed();
}