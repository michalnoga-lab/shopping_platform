package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String nip;
    private String street;
    private String streetNumber;
    private String city;
    private String postCode;
    private Boolean active;

    @Enumerated(EnumType.STRING)
    private Price defaultPrice;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "company")
    private Set<User> users = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "company")
    private Set<Product> products = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id) &&
                Objects.equals(name, company.name) &&
                Objects.equals(nip, company.nip) &&
                Objects.equals(street, company.street) &&
                Objects.equals(streetNumber, company.streetNumber) &&
                Objects.equals(city, company.city) &&
                Objects.equals(postCode, company.postCode) &&
                Objects.equals(active, company.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nip, street, streetNumber, city, postCode, active);
    }
}