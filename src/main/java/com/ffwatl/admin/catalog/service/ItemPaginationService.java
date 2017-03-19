package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.ProductImpl;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.GridFilter;
import org.springframework.data.domain.Page;

public interface ItemPaginationService {

    Page<ProductImpl> findAll(GridFilter filter);



}
