package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.EuroSizeImpl;

import java.util.List;

public interface EuroSizeDao {

    List<EuroSizeImpl> findByCat(CommonCategory cat);

    EuroSizeImpl findById(long id);

    void save(EuroSizeImpl eu);

    void removeSizeById(long id);

    List<EuroSizeImpl> findAll();

    List<EuroSizeImpl> findAllUsed();

}