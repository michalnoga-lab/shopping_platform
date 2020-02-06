package com.app.service;

import com.app.dto.CartDTO;
import com.app.dto.CompanyDTO;
import com.app.dto.ProductDTO;
import com.app.dto.ProductSearchDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.mappers.CartMapper;
import com.app.mappers.ProductMapper;
import com.app.model.Cart;
import com.app.model.OptimaCode;
import com.app.model.Product;
import com.app.model.User;
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

    public List<ProductDTO> getProductsOfCompany(CompanyDTO companyDTO) {
        if (companyDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE_PRODUCT, "getProductsOfCompany - company is null");
        }

        return productRepository
                .findAll()
                .stream()
                .filter(product -> product.getCompany().getId().equals((companyDTO.getId())))
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDTO getOneProduct(Long id) {
        if (id == null) {
            throw new AppException(ExceptionCodes.SERVICE_PRODUCT, "getOneProduct - id is null");
        }
        if (id < 0) {
            throw new AppException(ExceptionCodes.SERVICE_PRODUCT, "getOneProduct - id less than zero");
        }
        return productRepository
                .findById(id)
                .map(ProductMapper::toDto)
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_PRODUCT, "getOneProduct - no product with ID: " + id));
    }

    public Set<ProductDTO> getProductsOfCart(Long cartId) {
        if (cartId == null) {
            throw new AppException(ExceptionCodes.SERVICE_PRODUCT, "getProductsOfCart - cartId is null");
        }
        if (cartId < 0) {
            throw new AppException(ExceptionCodes.SERVICE_PRODUCT, "getProductsOfCart - cartId less than zero");
        }

        Optional<Cart> cartFromDb = cartRepository.findById(cartId);

        if (cartFromDb.isPresent()) {
            return cartFromDb.get().getProducts()
                    .stream()
                    .map(ProductMapper::toDto)
                    .collect(Collectors.toSet());
        } else {
            throw new AppException(ExceptionCodes.SERVICE_PRODUCT, "getOneProduct - no cart with ID: " + cartId);
        }
    }

    public List<ProductDTO> search(ProductSearchDTO productSearchDTO) {
        if (productSearchDTO == null) {
            throw new AppException(ExceptionCodes.CONTROLLERS, "search - search is null");
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
            throw new AppException(ExceptionCodes.SERVICE_PRODUCT, "addProducts - products list is null");
        }
        if (productDTOS.size() == 0) {
            throw new AppException(ExceptionCodes.SERVICE_USER, "addProducts - no products to add");
        }
        List<Product> products =
                productDTOS
                        .stream()
                        .map(ProductMapper::fromDto)
                        .collect(Collectors.toList());
        productRepository.saveAll(products);
    }

    public CartDTO removeFromCart(Long productId, Long userId) {

        Optional<Cart> cartOptional = cartRepository.findByUserId(userId); // TODO: 2020-01-22 find by cart ID
        Cart cart = cartOptional.orElseThrow(() ->
                new AppException(ExceptionCodes.SERVICE_PRODUCT, "removeFromCart - no cart with user ID: " + userId));
        Set<Product> products = cart.getProducts();
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new AppException(ExceptionCodes.SERVICE_PRODUCT, "removeFromCart - no product with ID: " + productId));
        products.remove(product);
        cartRepository.save(cart);
        return CartMapper.toDto(cart);
    }

    public void setCode(Long productId, String userInput) {
        if (productId == null) {
            throw new AppException(ExceptionCodes.SERVICE_PRODUCT, "setCode - product it null");
        }
        if (productId <= 0) {
            throw new AppException(ExceptionCodes.SERVICE_PRODUCT, "setCode - product Id less than zero");
        }
        if (userInput == null || userInput.length() == 0) {
            throw new AppException(ExceptionCodes.SERVICE_PRODUCT, "setCode - user input is null");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_PRODUCT, "setCode - no product with ID: " + productId));
        try {
            OptimaCode optimaCode = Arrays.stream(OptimaCode.values())
                    .filter(code -> code.getDescription().equals(userInput))
                    .findFirst()
                    .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_PRODUCT, "setCode - no optima code for enum"));
            product.setOptimaCode(optimaCode);
            productRepository.save(product);
        } catch (Exception e) {
            throw new AppException(ExceptionCodes.SERVICE_PRODUCT, "setCode - no optima code in enum: " + userInput);
        }
    }

    public void removeCode(Long productId) {
        if (productId == null) {
            throw new AppException(ExceptionCodes.SERVICE_PRODUCT, "removeCode - product it null");
        }
        if (productId <= 0) {
            throw new AppException(ExceptionCodes.SERVICE_PRODUCT, "removeCode- product Id less than zero");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_PRODUCT, "removeCode - no product with ID: " + productId));
        product.setOptimaCode(null);
        productRepository.save(product);
    }
}