package com.app.service;

import com.app.dto.*;
import com.app.exceptions.AppException;
import com.app.mappers.CompanyMapper;
import com.app.mappers.ProductInCartMapper;
import com.app.mappers.ProductMapper;
import com.app.model.*;
import com.app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CompanyRepository companyRepository;
    private final ProductCodeRepository productCodeRepository;
    private final ProductsInCartRepository productsInCartRepository;

    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();

        return products
                .stream()
                .filter(product -> !product.getHidden())
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsOfCompany(Long companyId) {
        if (companyId == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getProductsOfCompany - company is null");
        }

        return companyRepository
                .findAll()
                .stream()
                .filter(company -> company.getId().equals(companyId))
                .map(Company::getProducts)
                .flatMap(Collection::stream)
                .filter(product -> !product.getHidden())
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDTO addOneProduct(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "addOneProduct - productDTO is null");
        }

        Product product = ProductMapper.fromDto(productDTO);
        productRepository.save(product);
        return productDTO;
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

    public Set<ProductsInCartDTO> getProductsOfCart(Long cartId) {
        if (cartId == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getProductsOfCart - cartId is null");
        }
        if (cartId < 0) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getProductsOfCart - cartId less than zero");
        }

        return productsInCartRepository
                .findAll()
                .stream()
                .filter(product -> product.getCart().getId().equals(cartId))
                .map(ProductInCartMapper::toDto)
                .collect(Collectors.toSet());
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
                .filter(product -> !product.getHidden())
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
        products
                .forEach(product -> product.setHidden(false));

        productRepository.saveAll(products);
    }

    public ProductDTO setCode(Long productId, String userInput) {
        if (productId == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "setCode - product it null");
        }
        if (productId <= 0) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "setCode - product Id less than zero");
        }
        if (userInput == null || userInput.length() == 0) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "setCode - user input is null");
        }

        Optional<Product> productOptional = productRepository.findById(productId);
        ProductCode productCode = productCodeRepository.findByCodeFromOptima(userInput)
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_PRODUCT, "setCode - no product code in DB:" + userInput));

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setProductCode(productCode);
            productRepository.save(product);

            return ProductMapper.toDto(product);
        } else {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "setCode - no optima code in enum: " + userInput);
        }
    }

    public ProductDTO removeCode(Long productId) {
        if (productId == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "removeCode - product it null");
        }
        if (productId <= 0) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "removeCode- product ID less than zero");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_PRODUCT, "removeCode - no product with ID: " + productId));
        product.setProductCode(null); // tu był kiedyś enum
        productRepository.save(product);
        return ProductMapper.toDto(product);
    }

    public CompanyDTO hideAllProductsOfCompany(Long id) {
        if (id == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "hideAllProductsOfCompany - ID is null");
        }
        if (id < 0) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "hideAllProductsOfCompany - ID less than zero");
        }

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_PRODUCT, "hideAllProductsOfCompany - no company with ID: " + id));

        Set<Product> updatedProducts = company.getProducts();
        updatedProducts
                .forEach(product -> product.setHidden(true));

        company.setProducts(updatedProducts);
        companyRepository.save(company);
        return CompanyMapper.toDto(company);
    }
}