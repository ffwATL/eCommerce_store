package com.ffwatl.admin.catalog.dao;


import com.ffwatl.admin.catalog.domain.ProductImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaginationItemDao extends PagingAndSortingRepository<ProductImpl, Long> {

    @Query(value = "SELECT i FROM ProductImpl i WHERE i.salePrice BETWEEN ?1 AND ?2")
    Page<ProductImpl> findAllBySalePriceRange(double minPrice, double maxPrice, Pageable pageable);

    @Query(value = "SELECT i FROM ProductImpl i WHERE i.originPrice BETWEEN ?1 AND ?2")
    Page<ProductImpl> findAllByOriginPriceRange(double minPrice, double maxPrice, Pageable pageable);

    @Query(value = "SELECT i FROM ProductImpl i WHERE i.salePrice BETWEEN ?1 AND ?2 AND i.originPrice BETWEEN ?3 AND ?4")
    Page<ProductImpl> findAllByFilter(double minPriceSale, double maxPriceSale, double minPriceOrig,
                               double maxPriceOrig, Pageable pageable);

}