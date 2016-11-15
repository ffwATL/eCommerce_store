package com.ffwatl.admin.catalog.domain.filter.grid_filter;


public class GridFilterRuleImpl implements GridFilterRule{


    private String field;

    private String data;


    public String getField() {
        return field;
    }

    public GridFilterRule setField(String field) {
        this.field = field;
        return this;
    }

    public String getData() {
        return data;
    }

    public GridFilterRule setData(String data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "GridFilterRule{" +
                "field='" + field + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
