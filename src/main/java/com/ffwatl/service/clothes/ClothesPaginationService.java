package com.ffwatl.service.clothes;


import com.ffwatl.admin.filter.grid_filter.GridFilter;
import com.ffwatl.admin.entities.items.clothes.ClothesItem;
import org.springframework.data.domain.Page;

public interface ClothesPaginationService {

    Page<ClothesItem> findAllByFilter(GridFilter filter);

}
