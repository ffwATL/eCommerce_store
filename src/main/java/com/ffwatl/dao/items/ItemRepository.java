package com.ffwatl.dao.items;

import com.ffwatl.domain.items.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
}