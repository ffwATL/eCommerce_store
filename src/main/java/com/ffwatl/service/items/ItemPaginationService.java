package com.ffwatl.service.items;


import com.ffwatl.domain.filter.grid_filter.GridFilter;
import com.ffwatl.domain.items.Item;
import org.springframework.data.domain.Page;

public interface ItemPaginationService {

    Page<Item> findAll(GridFilter filter);



}
