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