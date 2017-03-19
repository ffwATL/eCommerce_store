package com.ffwatl.admin.catalog.dao;

import com.ffwatl.admin.catalog.domain.ProductImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface ItemRepository extends JpaRepository<ProductImpl, Long>, JpaSpecificationExecutor<ProductImpl> {
}
