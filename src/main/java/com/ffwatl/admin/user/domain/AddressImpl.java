package com.ffwatl.admin.user.domain;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressImpl implements Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = CountryImpl.class)
    private Country country;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = CityImpl.class)
    private City city;

    @Column(nullable = false)
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
        return "AddressImpl{" +
                "id=" + id +
                ", country=" + country +
                ", city=" + city +
                ", zipCode=" + zipCode +
                ", street='" + street + '\'' +
                '}';
    }
}