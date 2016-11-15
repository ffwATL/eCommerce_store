package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.EuroSize;
import com.ffwatl.admin.catalog.domain.EuroSizeImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EuroSizeRepository extends JpaRepository<EuroSizeImpl, Long>{

    List<EuroSize> findByCat(CommonCategory cat);
}