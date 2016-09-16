package com.ffwatl.service.items;


import com.ffwatl.domain.items.color.Color;

import java.util.List;

public interface ColorService {

    Color findById(long id);

    void save(Color c);

    void save(List<Color> list);

    List<Color> findAll();

}