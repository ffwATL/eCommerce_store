package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.domain.ProductDefault;
import com.ffwatl.admin.catalog.dao.ItemRepository;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.GridFilter;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.GridFilterRule;
import com.ffwatl.admin.catalog.domain.filter.specification.ProductDefaultSpecification;
import com.ffwatl.admin.web.service.PaginationService;
import com.ffwatl.admin.web.service.SortProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ItemPaginationServiceImpl extends PaginationService implements ItemPaginationService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Page<ProductDefault> findAll(GridFilter f) {
        SortProperties p = getProperties(f.getSortOption());
        PageRequest request = new PageRequest(f.getPge(), f.getPgeSize(), p.getDirection(), p.getColumn());
        Map<String, List<GridFilterRule>> map = f.getRules();
        ProductDefaultSpecification<ProductDefault> itemSpec = new ProductDefaultSpecification<>();
        Specifications<ProductDefault> spec = Specifications
                .where(itemSpec.isInPriceRange(map.get("originPrice")))
                .and(itemSpec.isInPriceRange(map.get("salePrice")));
        if(map.get("isActive") != null) spec = spec.and(itemSpec.isActive(map.get("isActive")));
        if(map.get("isSale") != null) spec = spec.and(itemSpec.isSale(map.get("isSale")));
        Page<ProductDefault> result = itemRepository.findAll(spec, request);
        for(ProductDefault i: result.getContent()){
            i.getCategory().setChild(null);
        }
        return result;
    }

    @Override
    protected SortProperties getProperties(String option){
        SortProperties p = new SortProperties();
        switch (option){
            case "1": return p.setColumn("importDate").setDirection(Sort.Direction.DESC);
            case "2": return p.setColumn("importDate").setDirection(Sort.Direction.ASC);
            case "3": return p.setColumn("originPrice").setDirection(Sort.Direction.ASC);
            case "4": return p.setColumn("originPrice").setDirection(Sort.Direction.DESC);
            case "5": return p.setColumn("salePrice").setDirection(Sort.Direction.ASC);
            case "6": return p.setColumn("salePrice").setDirection(Sort.Direction.DESC);
            default: return p.setColumn("importDate").setDirection(Sort.Direction.DESC);
        }
    }
}