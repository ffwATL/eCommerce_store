package com.ffwatl.service.clothes;


import com.ffwatl.domain.filter.grid_filter.GridFilter;
import com.ffwatl.domain.items.clothes.ClothesItem;
import org.springframework.data.domain.Page;

public interface ClothesPaginationService {

    Page<ClothesItem> findAllByFilter(GridFilter filter);

}
