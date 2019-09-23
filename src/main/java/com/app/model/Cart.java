package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(id, cart.id) &&
                Objects.equals(totalNetValue, cart.totalNetValue) &&
                Objects.equals(totalVatValue, cart.totalVatValue) &&
                Objects.equals(totalGrossValue, cart.totalGrossValue) &&
                Objects.equals(cartClosed, cart.cartClosed) &&
                Objects.equals(products, cart.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalNetValue, totalVatValue, totalGrossValue, cartClosed, products, user);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", totalNetValue=" + totalNetValue +
                ", totalVatValue=" + totalVatValue +
                ", totalGrossValue=" + totalGrossValue +
                ", cartClosed=" + cartClosed +
                ", products=" + products +
                ", user=" + user +
                '}';
    }
}