package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.ProductDefault;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.GridFilter;
import org.springframework.data.domain.Page;

public interface ItemPaginationService {

    Page<ProductDefault> findAll(GridFilter filter);



}
