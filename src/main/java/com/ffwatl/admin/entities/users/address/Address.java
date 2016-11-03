package com.ffwatl.admin.entities.users.address;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Country country;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private City city;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private ZipCode zipCode;

    private String street;

    public long getId() {
        return id;
    }

    public Country getCountry() {
        return country;
    }

    public City getCity() {
        return city;
    }

    public ZipCode getZipCode() {
        return zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setZipCode(ZipCode zipCode) {
        this.zipCode = zipCode;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}