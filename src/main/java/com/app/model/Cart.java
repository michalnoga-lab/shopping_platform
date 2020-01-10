package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private LocalDateTime purchaseTime;

    @OneToOne
    private DeliveryAddress deliveryAddress;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    /*@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "cart")
    private Set<Product> products= new HashSet<>();*/ // TODO: 2020-01-10 remove


    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "cart_product",
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
                Objects.equals(purchaseTime, cart.purchaseTime) &&
                Objects.equals(deliveryAddress, cart.deliveryAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalNetValue, totalVatValue, totalGrossValue, cartClosed, purchaseTime, deliveryAddress);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", totalNetValue=" + totalNetValue +
                ", totalVatValue=" + totalVatValue +
                ", totalGrossValue=" + totalGrossValue +
                ", cartClosed=" + cartClosed +
                ", purchaseTime=" + purchaseTime +
                ", deliveryAddress=" + deliveryAddress +
                '}';
    }
}