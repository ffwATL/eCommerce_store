package com.ffwatl.dao.clothes;


import com.ffwatl.admin.entities.items.CommonCategory;
import com.ffwatl.admin.entities.items.clothes.size.EuroSize;

import java.util.List;

public interface EuroSizeDao {

    List<EuroSize> findByCat(CommonCategory cat);

    EuroSize findById(long id);

    void save(EuroSize eu);

    void removeSizeById(long id);

    List<EuroSize> findAll();

    List<EuroSize> findAllUsed();

}