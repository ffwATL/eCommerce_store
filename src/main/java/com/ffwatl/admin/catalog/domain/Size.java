package com.ffwatl.admin.catalog.domain;

import java.util.List;


public interface Size extends Comparable<Size> {
    long getId();

    EuroSize getEu_size();

    int getQuantity();

    List<Field> getMeasurements();

    Size setId(long id);

    Size setQuantity(int quantity);

    Size setMeasurements(List<Field> measurements);

    Size setEu_size(EuroSize eu_size);
}
