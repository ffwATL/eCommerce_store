package com.ffwatl.dao.items;


import com.ffwatl.domain.items.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaginationItemDao extends PagingAndSortingRepository<Item, Long> {

    @Query(value = "SELECT i FROM Item i WHERE i.salePrice BETWEEN ?1 AND ?2")
    Page<Item> findAllBySalePriceRange(double minPrice, double maxPrice, Pageable pageable);

    @Query(value = "SELECT i FROM Item i WHERE i.originPrice BETWEEN ?1 AND ?2")
    Page<Item> findAllByOriginPriceRange(double minPrice, double maxPrice, Pageable pageable);

    @Query(value = "SELECT i FROM Item i WHERE i.salePrice BETWEEN ?1 AND ?2 AND i.originPrice BETWEEN ?3 AND ?4")
    Page<Item> findAllByFilter(double minPriceSale, double maxPriceSale, double minPriceOrig,
                               double maxPriceOrig, Pageable pageable);

}