package com.app.mappers;

import com.app.dto.ProductDTO;
import com.app.model.Product;

public interface ProductMapper {

    static ProductDTO toDto(Product product) { // TODO: 21.08.2020 poprawić całość
        return product == null ? null : ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .numberInAuction(product.getNumberInAuction())
                .auctionIndex(product.getAuctionIndex())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .nettPrice(product.getNettPrice())
                .vat(product.getVat())
                .grossPrice(product.getGrossPrice())
                .productCodeDTO(product.getProductCode() == null ? null : ProductCodeMapper.toDto(product.getProductCode()))
                .companyDTO(product.getCompany() == null ? null : CompanyMapper.toDto(product.getCompany()))
                .hidden(product.getHidden())
                .build();
    }

    static Product fromDto(ProductDTO productDTO) {
        return productDTO == null ? null : Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .numberInAuction(productDTO.getNumberInAuction())
                .auctionIndex(productDTO.getAuctionIndex())
                .description(productDTO.getDescription())
                .quantity(productDTO.getQuantity())
                .nettPrice(productDTO.getNettPrice())
                .vat(productDTO.getVat())
                .grossPrice(productDTO.getGrossPrice())
                .productCode(productDTO.getProductCodeDTO() == null ? null : ProductCodeMapper.fromDto(productDTO.getProductCodeDTO()))
                .company(productDTO.getCompanyDTO() == null ? null : CompanyMapper.fromDto(productDTO.getCompanyDTO()))
                .hidden(productDTO.getHidden())
                .build();
    }
}