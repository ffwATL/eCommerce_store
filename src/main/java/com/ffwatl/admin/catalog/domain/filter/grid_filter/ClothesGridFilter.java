package com.ffwatl.admin.catalog.domain.filter.grid_filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ClothesGridFilter extends ItemGridFilter{

    private final Map<String, String> params;

    private Map<String, List<GridFilterRule>> listMap = new HashMap<>();

    public ClothesGridFilter(Map<String, String> params){
        super(params);
        this.params = params;
    }

    @Override
    public Map<String, List<GridFilterRule>> getRules() {
        if(listMap.size() < 1) {
            this.listMap = super.getRules();
        }
        else {
            return this.listMap;
        }
        listMap.put("size", fillRule("id", params.get("size")));
        listMap.put("brand", fillRule("id", params.get("brand")));
        listMap.put("color", fillRule("id", params.get("color")));
        listMap.put("gender", fillRule("gender", params.get("gender")));
        return listMap;
    }

}