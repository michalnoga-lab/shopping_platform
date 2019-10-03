package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
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

  /*  @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, mappedBy = "cart")
    private Set<Product> products;*/ // TODO: 2019-10-03

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "join_cart_product",
            joinColumns = {@JoinColumn(name = "cart_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")}
    )
    private Set<Product> products;

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