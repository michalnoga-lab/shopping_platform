package com.app.dto;

import com.app.model.Cart;
import com.app.model.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Integer quantity;
    private BigDecimal nettPrice;
    private CompanyDTO companyDTO;
}