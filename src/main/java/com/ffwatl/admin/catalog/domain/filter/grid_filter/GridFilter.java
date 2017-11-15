package com.ffwatl.admin.catalog.domain.filter.grid_filter;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class GridFilter {

    private static final String REG_EXP = "\\|";

    private final Map<String, String> params;
    private String option = "1";
    private int pge = 0;
    private int pgeSize = 4;

    public GridFilter(Map<String, String> params){
        this.params = params;
    }

    public abstract Map<String, List<GridFilterRule>> getRules();

    public int getPge(){
        String pge = params.get("pge");
        if(pge != null) this.pge = Integer.valueOf(pge);
        return this.pge;
    }

    public int getPgeSize(){
        String pgeSize = params.get("pgeSize");
        if(pgeSize != null) this.pgeSize = Integer.parseInt(pgeSize);
        return this.pgeSize;
    }

    public String getSortOption() {
        String option = params.get("option");
        if(option == null) return this.option;
        this.option = option;
        return this.option;
    }

    protected List<GridFilterRule> fillRule(String field, String longString){
        if (longString == null || longString.length() < 1) return null;
        String[] values = longString.split(REG_EXP);
        List<GridFilterRule> list = new ArrayList<>();
        for(String s: values){
            list.add(new GridFilterRuleImpl().setData(s).setField(field));
            if(field.equals("locale_en")) list.add(new GridFilterRuleImpl().setData(s).setField("locale_ru"));
        }
        return list;
    }
}