package com.ffwatl.service.items;


import com.ffwatl.dao.items.ItemRepository;
import com.ffwatl.admin.filter.grid_filter.GridFilter;
import com.ffwatl.admin.filter.grid_filter.GridFilterRule;
import com.ffwatl.admin.filter.specification.ItemSpecification;
import com.ffwatl.admin.entities.items.Item;
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
public class ItemPaginationServiceImpl extends PaginationService implements ItemPaginationService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Page<Item> findAll(GridFilter f) {
        SortProperties p = getProperties(f.getSortOption());
        PageRequest request = new PageRequest(f.getPge(), f.getPgeSize(), p.getDirection(), p.getColumn());
        Map<String, List<GridFilterRule>> map = f.getRules();
        ItemSpecification<Item> itemSpec = new ItemSpecification<>();
        Specifications<Item> spec = Specifications
                .where(itemSpec.isInPriceRange(map.get("originPrice")))
                .and(itemSpec.isInPriceRange(map.get("salePrice")));
        if(map.get("isActive") != null) spec = spec.and(itemSpec.isActive(map.get("isActive")));
        if(map.get("isSale") != null) spec = spec.and(itemSpec.isSale(map.get("isSale")));
        Page<Item> result = itemRepository.findAll(spec, request);
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