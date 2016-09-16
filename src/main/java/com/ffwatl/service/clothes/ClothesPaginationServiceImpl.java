package com.ffwatl.service.clothes;


import com.ffwatl.dao.items.ClothesItemRepository;
import com.ffwatl.domain.filter.specification.ClothesItemSpecifications;
import com.ffwatl.domain.filter.grid_filter.GridFilter;
import com.ffwatl.domain.filter.grid_filter.GridFilterRule;
import com.ffwatl.domain.items.Item;
import com.ffwatl.domain.items.clothes.ClothesItem;
import com.ffwatl.service.PaginationService;
import com.ffwatl.service.SortProperties;
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
    private ClothesItemRepository clothesItemRepository;

    @Override
    public Page<ClothesItem> findAllByFilter(GridFilter filter){
        SortProperties p = getProperties(filter.getSortOption());
        PageRequest request = new PageRequest(filter.getPge(), filter.getPgeSize(), p.getDirection(), p.getColumn());
        Map<String, List<GridFilterRule>> map = filter.getRules();
        ClothesItemSpecifications spec = new ClothesItemSpecifications();
        Specifications<ClothesItem> res = Specifications
                .where(spec.isInPriceRange(map.get("originPrice")))
                .and(spec.isInPriceRange(map.get("salePrice")));
        if(map.get("itemGroup") != null) res = res.and(spec.isGroupEquals(map.get("itemGroup")));
        if(map.get("size") != null) res = res.and(spec.isSizeEquals(map.get("size")));
        if(map.get("color") != null) res = res.and(spec.isColorEquals(map.get("color")));
        if(map.get("brand") != null) res = res.and(spec.isBrandEquals(map.get("brand")));
        if(map.get("isActive") != null) res = res.and(spec.isActive(map.get("isActive")));
        if(map.get("isSale") != null) res = res.and(spec.isSale(map.get("isSale")));
        if(map.get("gender") != null) res = res.and(spec.isGenderEquals(map.get("gender")));
        Page<ClothesItem> result = clothesItemRepository.findAll(res, request);
        for(Item i: result.getContent()){
            i.getItemGroup().setChild(null);
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