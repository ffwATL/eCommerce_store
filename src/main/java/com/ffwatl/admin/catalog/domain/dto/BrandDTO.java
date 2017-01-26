package com.ffwatl.admin.catalog.domain.dto;

import com.ffwatl.admin.catalog.domain.Brand;
import com.ffwatl.admin.i18n.domain.I18n;



public class BrandDTO implements Brand {

    private long id;

    private String name;

    private I18n description;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public I18n getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Brand setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Brand setDescription(I18n description) {
        this.description = description;
        return this;
    }

    @Override
    public Brand setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BrandDTO brandDTO = (BrandDTO) o;

        if (getId() != brandDTO.getId()) return false;
        if (getName() != null ? !getName().equals(brandDTO.getName()) : brandDTO.getName() != null) return false;
        return !(getDescription() != null ? !getDescription().equals(brandDTO.getDescription()) : brandDTO.getDescription() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BrandDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description=" + description +
                '}';
    }
}