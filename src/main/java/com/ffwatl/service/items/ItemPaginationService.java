package com.ffwatl.service.items;


import com.ffwatl.manage.filter.grid_filter.GridFilter;
import com.ffwatl.manage.entities.items.Item;
import org.springframework.data.domain.Page;

public interface ItemPaginationService {

    Page<Item> findAll(GridFilter filter);



}
