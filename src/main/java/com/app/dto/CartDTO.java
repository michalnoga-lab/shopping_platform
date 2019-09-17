package com.app.dto;

import com.app.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartDTO {

    private Long id;
    private BigDecimal totalNetValue;
    private BigDecimal totalVatValue;
    private BigDecimal totalGrossValue;
    private boolean cartClosed;
    private Customer customer;
}