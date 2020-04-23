package com.app.mappers;

import com.app.dto.ProductDTO;
import com.app.model.Cart;
import com.app.model.Company;
import com.app.model.Product;

import java.util.HashSet;

public interface ProductMapper {

    static ProductDTO toDto(Product product) {
        return product == null ? null : ProductDTO.builder()
                .id(product.getId())
                .companyDTO(product.getCompany() == null ? null : CompanyMapper.toDto(product.getCompany()))
                .description(product.getDescription())
                .name(product.getName())
                .nettPrice(product.getNettPrice())
                .quantity(product.getQuantity())
                .numberInAuction(product.getNumberInAuction())
                .grossPrice(product.getGrossPrice())
                .vat(product.getVat())
                .auctionIndex(product.getAuctionIndex())
                .productCodeDTO(product.getProductCode() == null ? null : ProductCodeMapper.toDto(product.getProductCode()))
                .hidden(product.getHidden())
                .build();
    }

    static Product fromDto(ProductDTO productDTO) {
        return productDTO == null ? null : Product.builder()
                .id(productDTO.getId())
                .company(productDTO.getCompanyDTO() == null ? null : CompanyMapper.fromDto(productDTO.getCompanyDTO()))
                .description(productDTO.getDescription())
                .name(productDTO.getName())
                .nettPrice(productDTO.getNettPrice())
                .quantity(productDTO.getQuantity())
                .carts(new HashSet<>())
                .grossPrice(productDTO.getGrossPrice())
                .numberInAuction(productDTO.getNumberInAuction())
                .vat(productDTO.getVat())
                .auctionIndex(productDTO.getAuctionIndex())
                .productCode(productDTO.getProductCodeDTO() == null ? null : ProductCodeMapper.fromDto(productDTO.getProductCodeDTO()))
                .hidden(productDTO.getHidden())
                .build();
    }
}