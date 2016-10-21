package com.ffwatl.service.items;


import com.ffwatl.manage.entities.items.CommonCategory;
import com.ffwatl.manage.entities.items.clothes.size.EuroSize;

import java.util.List;

public interface EuroSizeService {

    EuroSize findById(long id);

    void save(EuroSize eu);

    void save(List<EuroSize> list);

    void removeSizeById(long id);

    List<EuroSize> findAll();

    List<EuroSize> findByCat(CommonCategory cat);

    List<EuroSize> findAllUsed();

}
