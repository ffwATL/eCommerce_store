package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.ProductClothes;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.GridFilter;
import org.springframework.data.domain.Page;

public interface ClothesPaginationService {

    Page<ProductClothes> findAllByFilter(GridFilter filter);

}
