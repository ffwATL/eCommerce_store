package com.ffwatl.dao.items;


import com.ffwatl.domain.items.CommonCategory;
import com.ffwatl.domain.items.clothes.size.EuroSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EuroSizeRepository extends JpaRepository<EuroSize, Long>{

    List<EuroSize> findByCat(CommonCategory cat);
}