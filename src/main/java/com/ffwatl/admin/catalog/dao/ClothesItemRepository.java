package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.ProductClothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClothesItemRepository extends JpaRepository<ProductClothes, Long>, JpaSpecificationExecutor<ProductClothes> {

}