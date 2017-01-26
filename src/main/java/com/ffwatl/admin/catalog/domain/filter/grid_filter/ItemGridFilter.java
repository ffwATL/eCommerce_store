package com.ffwatl.admin.catalog.domain.filter.grid_filter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemGridFilter extends GridFilter{

    private final Map<String, String> params;

    private final Map<String, List<GridFilterRule>> listMap = new HashMap<>();

    public ItemGridFilter(Map<String, String> params) {
        super(params);
        this.params = params;
    }

    @Override
    public Map<String, List<GridFilterRule>> getRules() {
        if(listMap.size() > 0) return this.listMap;
        listMap.put("name", fillRule("name", params.get("name")));
        listMap.put("color", fillRule("id", params.get("color")));
        listMap.put("salePrice", fillRule("salePrice", params.get("salePrice")));
        listMap.put("originPrice", fillRule("originPrice", params.get("originPrice")));
        listMap.put("itemGroup", fillRule("id", params.get("itemGroup")));
        listMap.put("isActive", fillRule("isActive", params.get("isActive")));
        listMap.put("isSale", fillRule("discount",params.get("isSale")));
        return this.listMap;
    }
}