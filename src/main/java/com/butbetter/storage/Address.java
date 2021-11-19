package com.butbetter.storage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Address {

    @Id
    @GeneratedValue
    private UUID uuid;
    private String name;
    private String companyName;
    private String street;
    private String city;
    private String postCode;
    private String country;

    public Address(UUID uuid, String name, String companyName, String street, String city, String postCode, String country) {
        this.uuid = uuid;
        this.name = name;
        this.companyName = companyName;
        this.street = street;
        this.city = city;
        this.postCode = postCode;
        this.country = country;
    }

    public Address() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(uuid, address.uuid)
                && Objects.equals(name, address.name)
                && Objects.equals(companyName, address.companyName)
                && Objects.equals(street, address.street)
                && Objects.equals(city, address.city)
                && Objects.equals(postCode, address.postCode)
                && Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, companyName, street, city, postCode, country);
    }

    @Override
    public String toString() {
        return "Address{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", companyName='" + companyName + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postCode='" + postCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
