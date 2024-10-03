package com.ossant.domain;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Address {

    private String address;

    private String city;

    private String state;

    private String zipCode;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address1 = (Address) o;
        return Objects.equals(address, address1.address) && Objects.equals(city, address1.city) &&
                Objects.equals(state, address1.state) && Objects.equals(zipCode, address1.zipCode);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(address);
        result = 31 * result + Objects.hashCode(city);
        result = 31 * result + Objects.hashCode(state);
        result = 31 * result + Objects.hashCode(zipCode);
        return result;
    }
}
