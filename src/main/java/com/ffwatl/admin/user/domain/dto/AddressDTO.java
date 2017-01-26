package com.ffwatl.admin.user.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ffwatl.admin.user.domain.Address;
import com.ffwatl.admin.user.domain.City;
import com.ffwatl.admin.user.domain.Country;


public class AddressDTO implements Address {

    private long id;

    @JsonDeserialize(as=CountryDTO.class)
    private Country country;

    @JsonDeserialize(as=CityDTO.class)
    private City city;

    private int zipCode;

    private String street;


    @Override
    public long getId() {
        return id;
    }

    @Override
    public Country getCountry() {
        return country;
    }

    @Override
    public City getCity() {
        return city;
    }

    @Override
    public int getZipCode() {
        return zipCode;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public Address setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public Address setCountry(Country country) {
        this.country = country;
        return this;
    }

    @Override
    public Address setCity(City city) {
        this.city = city;
        return this;
    }

    @Override
    public Address setZipCode(int zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    @Override
    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressDTO that = (AddressDTO) o;

        if (getId() != that.getId()) return false;
        if (getZipCode() != that.getZipCode()) return false;
        if (getCountry() != null ? !getCountry().equals(that.getCountry()) : that.getCountry() != null) return false;
        if (getCity() != null ? !getCity().equals(that.getCity()) : that.getCity() != null) return false;
        return !(getStreet() != null ? !getStreet().equals(that.getStreet()) : that.getStreet() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getCountry() != null ? getCountry().hashCode() : 0);
        result = 31 * result + (getCity() != null ? getCity().hashCode() : 0);
        result = 31 * result + getZipCode();
        result = 31 * result + (getStreet() != null ? getStreet().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "id=" + id +
                ", country=" + country +
                ", city=" + city +
                ", zipCode=" + zipCode +
                ", street='" + street + '\'' +
                '}';
    }
}