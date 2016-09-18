package com.ffwatl.dao.items;


import com.ffwatl.manage.entities.items.CommonCategory;
import com.ffwatl.manage.entities.items.clothes.size.EuroSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EuroSizeRepository extends JpaRepository<EuroSize, Long>{

    List<EuroSize> findByCat(CommonCategory cat);
}