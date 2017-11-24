package com.ffwatl.admin.catalog.domain.dto.response;

import java.util.List;

/**
 * @author mmed 11/15/17
 */
public interface Catalog {
    long getTotalItems();

    void setTotalItems(long totalItems);

    int getPge();

    void setPge(int pge);

    int getPgeSize();

    void setPgeSize(int pgeSize);

    int getTotalPages();

    void setTotalPages(int totalPages);

    List<CatalogItemImpl> getItems();
}
