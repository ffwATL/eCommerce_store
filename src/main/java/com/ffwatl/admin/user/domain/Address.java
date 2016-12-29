package com.ffwatl.admin.user.domain;


public interface Address {
    long getId();

    Country getCountry();

    City getCity();

    int getZipCode();

    String getStreet();

    Address setId(long id);

    Address setCountry(Country country);

    Address setCity(City city);

    Address setZipCode(int zipCode);

    Address setStreet(String street);
}