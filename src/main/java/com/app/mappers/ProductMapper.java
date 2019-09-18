package com.app.mappers;

import com.app.dto.ProductDTO;
import com.app.model.Product;

public interface ProductMapper {

    static ProductDTO toDto(Product product){
        return product==null?null: ProductDTO.builder()
                .id(product.getId())
                .cart(product.getCart())
                .company(product.getCompany())
                .description(product.getDescription())
                .name(product.getName())
                .nettPrice(product.getNettPrice())
                .quantity(product.getQuantity())
                .build();
    }

    static Product fromDto(ProductDTO productDTO){
        return productDTO==null?null: Product.builder()
                .id(productDTO.getId())
                .cart(productDTO.getCart())
                .company(productDTO.getCompany())
                .description(productDTO.getDescription())
                .name(productDTO.getName())
                .nettPrice(productDTO.getNettPrice())
                .quantity(productDTO.getQuantity())
                .build();
    }
}