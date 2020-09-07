package com.app.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "products_in_cart")
public class ProductsInCart {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long productId;
    private Integer quantity;
    private BigDecimal nettPrice;
    private Double vat;
    private BigDecimal grossPrice;
    private Boolean hidden;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsInCart that = (ProductsInCart) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(productId, that.productId) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(nettPrice, that.nettPrice) &&
                Objects.equals(vat, that.vat) &&
                Objects.equals(grossPrice, that.grossPrice) &&
                Objects.equals(hidden, that.hidden);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, quantity, nettPrice, vat, grossPrice, hidden);
    }
}