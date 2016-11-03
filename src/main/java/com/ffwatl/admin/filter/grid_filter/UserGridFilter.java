package com.ffwatl.admin.filter.grid_filter;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserGridFilter extends GridFilter{


    private final Map<String, String> params;
    private final Map<String, List<GridFilterRule>> listMap = new HashMap<>();

    public UserGridFilter(Map<String, String> params) {
        super(params);
        this.params = params;
    }

    @Override
    public Map<String, List<GridFilterRule>> getRules() {
        if(listMap.size() > 0) return this.listMap;
        /*listMap.put("name", fillRule("name", params.get("name")));*/
        return this.listMap;
    }
}
