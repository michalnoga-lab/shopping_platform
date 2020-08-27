package com.app.dto;

import com.app.model.Cart;
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
public class ProductsInCartDTO {

    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal nettPrice;
    private Double vat;
    private BigDecimal grossPrice;
    private Boolean hidden;
    private Cart cart;
}