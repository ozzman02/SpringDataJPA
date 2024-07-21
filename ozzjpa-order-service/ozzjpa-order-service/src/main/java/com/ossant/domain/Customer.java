package com.ossant.domain;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@AttributeOverrides({
        @AttributeOverride(
                name = "address.address",
                column = @Column(name = "address")
        ),
        @AttributeOverride(
                name = "address.city",
                column = @Column(name = "city")
        ),
        @AttributeOverride(
                name = "address.state",
                column = @Column(name = "state")
        ),
        @AttributeOverride(
                name = "address.zipCode",
                column = @Column(name = "zip_code")
        )
})
public class Customer extends BaseEntity {

    private String customerName;

    private String phone;

    private String email;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "customer")
    private Set<OrderHeader> orders = new LinkedHashSet<>();

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<OrderHeader> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderHeader> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Customer customer = (Customer) o;
        return Objects.equals(customerName, customer.customerName)
                && Objects.equals(phone, customer.phone)
                && Objects.equals(email, customer.email)
                && Objects.equals(address, customer.address)
                && Objects.equals(orders, customer.orders);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Objects.hashCode(customerName);
        result = 31 * result + Objects.hashCode(phone);
        result = 31 * result + Objects.hashCode(email);
        result = 31 * result + Objects.hashCode(address);
        return result;
    }
}
