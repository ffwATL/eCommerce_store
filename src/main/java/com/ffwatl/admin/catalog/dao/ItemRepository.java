package com.ffwatl.admin.catalog.dao;

import com.ffwatl.admin.catalog.domain.ProductDefault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface ItemRepository extends JpaRepository<ProductDefault, Long>, JpaSpecificationExecutor<ProductDefault> {
}
