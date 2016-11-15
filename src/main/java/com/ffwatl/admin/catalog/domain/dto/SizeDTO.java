package com.ffwatl.admin.catalog.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ffwatl.admin.catalog.domain.EuroSize;
import com.ffwatl.admin.catalog.domain.Field;
import com.ffwatl.admin.catalog.domain.Size;

import java.util.List;



public class SizeDTO implements Size{

    private long id;
    private int quantity;
    @JsonDeserialize(contentAs=FieldDTO.class)
    private List<Field> measurements;
    @JsonDeserialize(as=EuroSizeDTO.class)
    private EuroSize eu_size;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public EuroSize getEu_size() {
        return eu_size;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public List<Field> getMeasurements() {
        return measurements;
    }

    @Override
    public Size setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Size setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public Size setMeasurements(List<Field> measurements) {
        this.measurements = measurements;
        return this;
    }

    @Override
    public Size setEu_size(EuroSize eu_size) {
        this.eu_size = eu_size;
        return this;
    }

    @Override
    public int compareTo(Size o) {
        if(o == null) return 1;
        return this.eu_size.compareTo(o.getEu_size());
    }

    @Override
    public String toString() {
        return "SizeDTO{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", measurements=" + measurements +
                ", eu_size=" + eu_size +
                '}';
    }
}
