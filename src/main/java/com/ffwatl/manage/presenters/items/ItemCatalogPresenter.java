package com.ffwatl.manage.presenters.items;


import java.util.List;

public class ItemCatalogPresenter {

    private int pge;
    private int pgeSize;
    private int totalPages;
    private long totalItems;

    private final List<ItemCatalog> items;

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public ItemCatalogPresenter(List<ItemCatalog> items){
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

    public List<ItemCatalog> getItems() {
        return items;
    }
}