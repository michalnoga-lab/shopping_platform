package com.app.model;

import com.app.dto.ProductCodeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.validator.constraints.Length;

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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String numberInAuction;
    private String auctionIndex;

    @Column(length = 5000)
    @Length(max = 5000)
    private String description;

    private Integer quantity;
    private BigDecimal nettPrice;
    private Double vat;
    private BigDecimal grossPrice;
    private String productCode;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "products")
    private Set<Cart> carts;

    private Boolean hidden;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(numberInAuction, product.numberInAuction) &&
                Objects.equals(auctionIndex, product.auctionIndex) &&
                Objects.equals(description, product.description) &&
                Objects.equals(quantity, product.quantity) &&
                Objects.equals(nettPrice, product.nettPrice) &&
                Objects.equals(vat, product.vat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, numberInAuction, auctionIndex, description, quantity, nettPrice, vat);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberInAuction='" + numberInAuction + '\'' +
                ", auctionIndex='" + auctionIndex + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", nettPrice=" + nettPrice +
                ", vat=" + vat +
                ", grossPrice=" + grossPrice +
                '}';
    }
}