package com.app.service;

import com.app.dto.ProductDTO;
import com.app.dto.ProductSearchDTO;
import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
import com.app.mappers.ProductMapper;
import com.app.model.Cart;
import com.app.model.OptimaCode;
import com.app.model.Product;
import com.app.repository.CartRepository;
import com.app.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();

        return products
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> findProductsOfCompany(Long companyId) {
        if (companyId == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getProductsOfCompany - company is null");
        }

        return productRepository
                .findAll()
                .stream()
                .filter(product -> product.getCompany().getId().equals((companyId)))
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDTO getOneProduct(Long id) {
        if (id == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getOneProduct - id is null");
        }
        if (id < 0) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getOneProduct - id less than zero");
        }
        return productRepository
                .findById(id)
                .map(ProductMapper::toDto)
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_PRODUCT, "getOneProduct - no product with ID: " + id));
    }

    public Set<ProductDTO> getProductsOfCart(Long cartId) {
        if (cartId == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getProductsOfCart - cartId is null");
        }
        if (cartId < 0) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getProductsOfCart - cartId less than zero");
        }

        Optional<Cart> cartFromDb = cartRepository.findById(cartId);

        if (cartFromDb.isPresent()) {
            return cartFromDb.get().getProducts()
                    .stream()
                    .map(ProductMapper::toDto)
                    .collect(Collectors.toSet());
        } else {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getOneProduct - no cart with ID: " + cartId);
        }
    }

    public List<ProductDTO> search(ProductSearchDTO productSearchDTO) {
        if (productSearchDTO == null) {
            throw new AppException(InfoCodes.CONTROLLERS, "search - search is null");
        }
        return productRepository
                .findAll()
                .stream()
                .filter(product ->
                        product.getName().toLowerCase().contains(productSearchDTO.getUserInput().toLowerCase()))
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public void addProducts(List<ProductDTO> productDTOS) {
        if (productDTOS == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "addProducts - products list is null");
        }
        if (productDTOS.size() == 0) {
            throw new AppException(InfoCodes.SERVICE_USER, "addProducts - no products to add");
        }
        List<Product> products =
                productDTOS
                        .stream()
                        .map(ProductMapper::fromDto)
                        .collect(Collectors.toList());
        productRepository.saveAll(products);
    }

    public void setCode(Long productId, String userInput) {
        if (productId == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "setCode - product it null");
        }
        if (productId <= 0) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "setCode - product Id less than zero");
        }
        if (userInput == null || userInput.length() == 0) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "setCode - user input is null");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_PRODUCT, "setCode - no product with ID: " + productId));
        try {
            OptimaCode optimaCode = Arrays.stream(OptimaCode.values())
                    .filter(code -> code.getDescription().equals(userInput))
                    .findFirst()
                    .orElseThrow(() -> new AppException(InfoCodes.SERVICE_PRODUCT, "setCode - no optima code for enum"));
            product.setOptimaCode(optimaCode);
            productRepository.save(product);
        } catch (Exception e) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "setCode - no optima code in enum: " + userInput);
        }
    }

    public void removeCode(Long productId) {
        if (productId == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "removeCode - product it null");
        }
        if (productId <= 0) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "removeCode- product Id less than zero");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_PRODUCT, "removeCode - no product with ID: " + productId));
        product.setOptimaCode(null);
        productRepository.save(product);
    }
}