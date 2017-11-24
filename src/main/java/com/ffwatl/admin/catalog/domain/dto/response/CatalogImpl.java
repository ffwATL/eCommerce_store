package com.ffwatl.admin.catalog.domain.dto.response;


import java.util.List;

public class CatalogImpl implements Catalog {

    private int pge;
    private int pgeSize;
    private int totalPages;
    private long totalItems;

    private final List<CatalogItemImpl> items;

    @Override
    public long getTotalItems() {
        return totalItems;
    }

    @Override
    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public CatalogImpl(List<CatalogItemImpl> items){
        this.items = items;
    }

    @Override
    public int getPge() {
        return pge;
    }

    @Override
    public void setPge(int pge) {
        this.pge = pge;
    }

    @Override
    public int getPgeSize() {
        return pgeSize;
    }

    @Override
    public void setPgeSize(int pgeSize) {
        this.pgeSize = pgeSize;
    }

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public List<CatalogItemImpl> getItems() {
        return items;
    }
}