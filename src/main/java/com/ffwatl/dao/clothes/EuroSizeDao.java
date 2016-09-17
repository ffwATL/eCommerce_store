package com.ffwatl.dao.clothes;


import com.ffwatl.domain.items.CommonCategory;
import com.ffwatl.domain.items.clothes.size.EuroSize;

import java.util.List;

public interface EuroSizeDao {

    List<EuroSize> findByCat(CommonCategory cat);

    EuroSize findById(long id);

    void save(EuroSize eu);

    List<EuroSize> findAll();

    List<EuroSize> findAllUsed();

}