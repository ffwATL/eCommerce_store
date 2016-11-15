package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.EuroSize;
import com.ffwatl.admin.catalog.domain.EuroSizeImpl;

import java.util.List;

public interface EuroSizeService {

    EuroSizeImpl findById(long id);

    void save(EuroSize eu);

    void save(List<EuroSize> list);

    void removeSizeById(long id);

    List<EuroSize> findAll();

    List<EuroSize> findByCat(CommonCategory cat);

    List<EuroSize> findAllUsed();

}
