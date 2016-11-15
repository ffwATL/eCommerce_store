package com.ffwatl.admin.catalog.domain.filter.grid_filter;



public interface GridFilterRule {

    String getField();

    String getData();

    GridFilterRule setField(String field);
    GridFilterRule setData(String data);

}