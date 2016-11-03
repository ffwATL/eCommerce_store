package com.ffwatl.dao.items;


import com.ffwatl.admin.entities.items.clothes.ClothesItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClothesItemRepository extends JpaRepository<ClothesItem, Long>, JpaSpecificationExecutor<ClothesItem> {

}