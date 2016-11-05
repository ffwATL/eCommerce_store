package com.ffwatl.service.items;


import com.ffwatl.admin.filter.grid_filter.GridFilter;
import com.ffwatl.admin.entities.items.DefaultItem;
import org.springframework.data.domain.Page;

public interface ItemPaginationService {

    Page<DefaultItem> findAll(GridFilter filter);



}
