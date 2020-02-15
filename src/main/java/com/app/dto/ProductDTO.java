package com.app.dto;

import com.app.model.OptimaCode;
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
    private String numberInAuction;
    private String auctionIndex;
    private String description;
    private Integer quantity;
    private BigDecimal nettPrice;
    private Double vat;
    private BigDecimal grossPrice;
    private OptimaCode optimaCode;
    private CompanyDTO companyDTO;
    private Boolean hidden;
}