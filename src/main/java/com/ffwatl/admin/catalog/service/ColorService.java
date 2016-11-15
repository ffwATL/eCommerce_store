package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.Color;

import java.util.List;

public interface ColorService {

    Color findById(long id);

    void save(Color c);

    void save(List<Color> list);

    List<Color> findAll();
    List<Color> findAllUsed();
}