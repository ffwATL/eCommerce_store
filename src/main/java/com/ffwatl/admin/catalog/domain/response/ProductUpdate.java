package com.ffwatl.admin.catalog.domain.response;

import com.ffwatl.admin.catalog.domain.Product;

import java.util.List;


public interface ProductUpdate extends Product {

    int[] getRemovedImgs();
    long[] getOldSizes();
    boolean isEdit();
    List<ProductImage> getImages();
    List<ProductImage> getThumbs();

    ProductUpdate setRemovedImgs(int[] removedImgs);
    ProductUpdate setOldSizes(long[] oldSizes);
    ProductUpdate setEdit(boolean edit);
    ProductUpdate setThumbs(List<ProductImage> thumbs);
    ProductUpdate setImages(List<ProductImage> images);
}