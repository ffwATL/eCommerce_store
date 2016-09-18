package com.ffwatl.dao.items;


import com.ffwatl.manage.entities.items.color.Color;

import java.util.List;

public interface ColorDao {

    Color findById(long id);

    void save(Color c);

    List<Color> findAll();

    List<Color> findAllUsed();
}