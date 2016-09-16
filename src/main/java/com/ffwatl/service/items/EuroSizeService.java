package com.ffwatl.service.items;


import com.ffwatl.domain.items.CommonCategory;
import com.ffwatl.domain.items.clothes.size.EuroSize;

import java.util.List;

public interface EuroSizeService {

    EuroSize findById(long id);

    void save(EuroSize eu);

    void save(List<EuroSize> list);

    List<EuroSize> findAll();

    List<EuroSize> findByCat(CommonCategory cat);

}
