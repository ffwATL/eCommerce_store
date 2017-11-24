package com.ffwatl.admin.catalog.domain.dto.response;

import java.util.List;

/**
 * @author mmed 11/23/17
 */
public class PageResponse<T> {

    private int page;
    private int totalPages;
    private int totalItems;
    private List<T> data;

    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public List<T> getData() {
        return data;
    }


    public void setPage(int page) {
        this.page = page;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}