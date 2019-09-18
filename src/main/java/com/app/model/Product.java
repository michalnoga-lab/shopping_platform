package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal nettPrice;
    @ManyToOne
    @JoinColumn(name = "cart_id") // TODO: 2019-09-18 many to many ???
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}