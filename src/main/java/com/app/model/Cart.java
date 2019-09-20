package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal totalNetValue;
    private BigDecimal totalVatValue;
    private BigDecimal totalGrossValue;
    private Boolean cartClosed;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "cart")
    private Set<Product> products;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private User user;
}