package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "company")
    private Set<Customer> customers;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "company")
    private Set<Product> products;
}