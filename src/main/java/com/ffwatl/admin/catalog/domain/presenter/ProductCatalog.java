package com.ffwatl.admin.catalog.domain.presenter;


import java.util.List;

public class ProductCatalog {

    private int pge;
    private int pgeSize;
    private int totalPages;
    private long totalItems;

    private final List<CatalogItem> items;

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public ProductCatalog(List<CatalogItem> items){
        this.items = items;
    }

    public int getPge() {
        return pge;
    }

    public void setPge(int pge) {
        this.pge = pge;
    }

    public int getPgeSize() {
        return pgeSize;
    }

    public void setPgeSize(int pgeSize) {
        this.pgeSize = pgeSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<CatalogItem> getItems() {
        return items;
    }
}