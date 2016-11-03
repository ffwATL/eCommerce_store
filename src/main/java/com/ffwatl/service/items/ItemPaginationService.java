package com.ffwatl.service.items;


import com.ffwatl.admin.filter.grid_filter.GridFilter;
import com.ffwatl.admin.entities.items.Item;
import org.springframework.data.domain.Page;

public interface ItemPaginationService {

    Page<Item> findAll(GridFilter filter);



}
