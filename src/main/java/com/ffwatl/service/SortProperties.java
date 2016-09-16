package com.ffwatl.service;

import org.springframework.data.domain.Sort;



public class SortProperties {

    private String column;
    private Sort.Direction direction;

    public String getColumn() {
        return column;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public SortProperties setColumn(String column) {
        this.column = column;
        return this;
    }

    public SortProperties setDirection(Sort.Direction direction) {
        this.direction = direction;
        return this;
    }
}
