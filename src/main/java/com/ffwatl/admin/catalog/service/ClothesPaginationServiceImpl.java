package com.ffwatl.admin.catalog.service;


import com.ffwatl.admin.catalog.dao.ItemRepository;
import com.ffwatl.admin.catalog.domain.ProductImpl;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.GridFilter;
import com.ffwatl.admin.catalog.domain.filter.grid_filter.GridFilterRule;
import com.ffwatl.admin.catalog.domain.filter.specification.ProductClothesSpecification;
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
public class ClothesPaginationServiceImpl extends PaginationService implements ClothesPaginationService {

    @Autowired
    private ItemRepository clothesItemRepository;

    @Override
    public Page<ProductImpl> findAllByFilter(GridFilter filter){
        SortProperties p = getProperties(filter.getSortOption());
        PageRequest request = new PageRequest(filter.getPge(), filter.getPgeSize(), p.getDirection(), p.getColumn());
        Map<String, List<GridFilterRule>> map = filter.getRules();
        ProductClothesSpecification spec = new ProductClothesSpecification();
        Specifications<ProductImpl> res = Specifications
                .where(spec.isInPriceRange(map.get("originPrice")))
                .and(spec.isInPriceRange(map.get("salePrice")));
        if(map.get("itemGroup") != null) res = res.and(spec.isGroupEquals(map.get("itemGroup")));
        if(map.get("size") != null) res = res.and(spec.isSizeEquals(map.get("size")));
        if(map.get("color") != null) res = res.and(spec.isColorEquals(map.get("color")));
        if(map.get("brand") != null) res = res.and(spec.isBrandEquals(map.get("brand")));
        if(map.get("isActive") != null) res = res.and(spec.isActive(map.get("isActive")));
        if(map.get("isSale") != null) res = res.and(spec.isSale(map.get("isSale")));
        if(map.get("gender") != null) res = res.and(spec.isGenderEquals(map.get("gender")));
        Page<ProductImpl> result = clothesItemRepository.findAll(res, request);
        for(ProductImpl i: result.getContent()){
            i.getProductCategory().setChild(null);
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