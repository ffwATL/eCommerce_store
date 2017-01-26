package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.CommonCategory;
import com.ffwatl.admin.catalog.domain.ProductAttributeType;
import com.ffwatl.admin.catalog.domain.ProductAttributeTypeImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EuroSizeRepository extends JpaRepository<ProductAttributeTypeImpl, Long>{

    List<ProductAttributeType> findByCat(CommonCategory cat);
}