package com.app.service;

import com.app.Utilities.CustomAddresses;
import com.app.Utilities.FileManager;
import com.app.dto.CartDTO;
import com.app.dto.ProductDTO;
import com.app.dto.ProductsInCartDTO;
import com.app.dto.UserDTO;
import com.app.exceptions.AppException;
import com.app.mappers.CartMapper;
import com.app.mappers.CompanyMapper;
import com.app.mappers.ProductCodeMapper;
import com.app.mappers.ProductInCartMapper;
import com.app.model.*;
import com.app.parsers.XmlParserOptima;
import com.app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final ProductsInCartRepository productsInCartRepository;
    private final CompanyRepository companyRepository;

    private final XmlParserOptima xmlParserOptima;
    private final EmailService emailService;

    public CartDTO getCart(Long cartId) {
        if (cartId == null) {
            throw new AppException(InfoCodes.SERVICE_CART, "getCart - cart ID is null");
        }
        if (cartId <= 0) {
            throw new AppException(InfoCodes.SERVICE_CART, "getCart - cart ID less than zero");
        }

        return cartRepository.findById(cartId)
                .stream()
                .map(cart -> {
                    cart.setUser(User.builder().build());
                    return CartMapper.toDto(cart);
                })
                .findFirst()
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "getCart - no cart with ID: " + cartId));
    }

    public CartDTO addProductToCart(ProductDTO productDTO, Long userId) {
        if (productDTO == null) {
            throw new AppException(InfoCodes.SERVICE_CART, "addProductToCart - product is null");
        } // TODO: 29.09.2020 od tego zacząć szukać null 

        User user = userRepository.getOne(userId);
        Optional<CartDTO> cartDTOOptional = getActiveCart(userId);

        Product product = productRepository.getOne(productDTO.getId());

        Cart cart;
        Optional<Cart> cartOptional = Optional.empty();

        if (cartDTOOptional.isPresent()) {
            cartOptional = cartRepository.findById(cartDTOOptional.get().getId());
        }

        cart = cartOptional.orElseGet(() -> Cart.builder()
                .totalNetValue(BigDecimal.ZERO)
                .totalVatValue(BigDecimal.ZERO)
                .totalGrossValue(BigDecimal.ZERO)
                .cartClosed(false)
                .build());

        ProductsInCart singleProductInCart = ProductsInCart.builder()
                .cart(cart)
                .name(product.getName())
                .productId(product.getId())
                .quantity(productDTO.getQuantity())
                .nettPrice(product.getNettPrice())
                .vat(product.getVat())
                .grossPrice(product.getGrossPrice())
                .hidden(false)
                .productCode(ProductCodeMapper.fromDto(productDTO.getProductCodeDTO()))
                .build();

        BigDecimal totalNettCartValue = calculateCartNettValue
                (singleProductInCart.getNettPrice(),
                        singleProductInCart.getQuantity());

        BigDecimal totalVatCartValue = calculateCartVatValue(totalNettCartValue, product.getVat());

        BigDecimal totalGrossCartValue = calculateTotalGrossValue(totalNettCartValue, totalVatCartValue);

        cart.setTotalNetValue(cart.getTotalNetValue().add(totalNettCartValue));
        cart.setTotalVatValue(cart.getTotalVatValue().add(totalVatCartValue));
        cart.setTotalGrossValue(cart.getTotalGrossValue().add(totalGrossCartValue));

        productsInCartRepository.save(singleProductInCart);
        cart.setUser(user);
        cartRepository.save(cart);

        CartDTO cartDTO = CartMapper.toDto(cart);

        Set<ProductsInCartDTO> productsInCartDTO = productsInCartRepository
                .findProductsInCartById(cart.getId())
                .stream()
                .filter(prod -> prod.getHidden().equals(false))
                .map(ProductInCartMapper::toDto)
                .collect(Collectors.toSet());

        cartDTO.setProductsInCartDTO(productsInCartDTO);
        cartDTO.setUserDTO(UserDTO.builder().build());

        return cartDTO;
    }

    public BigDecimal calculateCartNettValue(BigDecimal price, Integer quantity) {
        return BigDecimal.valueOf(quantity).multiply(price).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateCartVatValue(BigDecimal nettValue, Double vat) {
        return nettValue.multiply(BigDecimal.valueOf(vat / 100)).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateTotalGrossValue(BigDecimal nettValue, BigDecimal vatValue) {
        return nettValue.add(vatValue).setScale(2, RoundingMode.HALF_UP);
    }

    public List<ProductsInCartDTO> getAllProductsFromCart(Long cartId) {
        return productsInCartRepository
                .findAll()
                .stream()
                .filter(product -> product.getCart().getId().equals(cartId))
                .filter(product -> product.getHidden().equals(false))
                .map(ProductInCartMapper::toDto)
                .collect(Collectors.toList());
    }

    public CartDTO removeProductFromCart(Long productId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "removeProductFromCart - no user with ID: " + userId));

        Cart cart = cartRepository.findByUserId(userId)
                .stream()
                .filter(c -> c.getCartClosed().equals(false))
                .findFirst()
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "removeProductFromCart - no cart for user with ID: " + userId));

        ProductsInCart productToRemove = productsInCartRepository
                .findProductsInCartById(cart.getId())
                .stream()
                .filter(productsInCart -> productsInCart.getHidden().equals(false))
                .filter(productsInCart -> productsInCart.getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "removeProductFromCart - no product with ID: " + productId));


        productToRemove.setHidden(true);
        productsInCartRepository.save(productToRemove);

        BigDecimal nettValue = calculateCartNettValue(productToRemove.getNettPrice(), productToRemove.getQuantity());
        BigDecimal vatValue = calculateCartVatValue(nettValue, productToRemove.getVat());
        BigDecimal grossValue = calculateTotalGrossValue(nettValue, vatValue);

        cart.setTotalNetValue(cart.getTotalNetValue().subtract(nettValue));
        cart.setTotalVatValue(cart.getTotalVatValue().subtract(vatValue));
        cart.setTotalGrossValue(cart.getTotalGrossValue().subtract(grossValue));

        cartRepository.save(cart);
        CartDTO cartDTO = CartMapper.toDto(cart);

        Set<ProductsInCartDTO> productsInCarts = productsInCartRepository
                .findProductsInCartById(cart.getId())
                .stream().map(ProductInCartMapper::toDto)
                .collect(Collectors.toSet());

        cartDTO.setProductsInCartDTO(productsInCarts);
        return cartDTO;
    }

    public List<CartDTO> getAllUsersCarts(Long userId) {
        if (userId == null) {
            throw new AppException(InfoCodes.SERVICE_CART, "getAllUsersCarts - user ID is null");
        }
        List<CartDTO> usersCarts = new ArrayList<>();

        cartRepository.findAllByUserId(userId)
                .stream()
                .map(CartMapper::toDto)
                .filter(cartDTO -> cartDTO.getCartClosed().equals(true))
                .forEach(cart -> {
                    cart.setUserDTO(UserDTO.builder().build());
                    Set<ProductsInCartDTO> products = cart
                            .getProductsInCartDTO()
                            .stream()
                            .filter(p -> p.getHidden().equals(false))
                            .collect(Collectors.toSet());
                    cart.setProductsInCartDTO(products);
                    usersCarts.add(cart);
                });

        return usersCarts;
    }

    public Optional<CartDTO> getActiveCart(Long userId) {
        if (userId == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getActiveCart - user ID is null");
        }
        if (userId < 0) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getActiveCart - cart ID less than zero");
        }

        Optional<CartDTO> cartOptional = cartRepository
                .findAllByUserId(userId)
                .stream()
                .filter(cart -> cart.getCartClosed().equals(false))
                .map(CartMapper::toDto)
                .findFirst();

        cartOptional.ifPresent(cartDTO -> {
            cartDTO.setUserDTO(UserDTO.builder().build());
        });

        return cartOptional;

    }


    public List<ProductsInCartDTO> getProductsOfActiveCart(Long userId) {
        if (userId == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getProductsOfActiveCart - user ID is null");
        }
        if (userId < 0) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getProductsOfActiveCart - cart ID less than zero");
        }

        Optional<Cart> cartInDB = cartRepository
                .findAllByUserId(userId)
                .stream()
                .filter(cart -> cart.getCartClosed().equals(false))
                .findFirst();

        if (cartInDB.isEmpty()) {
            return new ArrayList<>();
        } else {

            return cartInDB.map(cart -> cart.getProductsInCart()
                    .stream()
                    .map(ProductInCartMapper::toDto)
                    .filter(product -> product.getHidden().equals(false))
                    .collect(Collectors.toList()))
                    .orElseGet(List::of);
        }
    }

    public CartDTO setAddressToCart(Long addressId, Long userId) {
        if (addressId == null) {
            throw new AppException(InfoCodes.SERVICE_CART, "setAddressToCart - ID is null");
        }
        if (addressId <= 0) {
            throw new AppException(InfoCodes.SERVICE_CART, "setAddressToCart - ID less than zero");
        }
        Optional<CartDTO> optionalCartDTO = getActiveCart(userId);
        CartDTO cartDTO = optionalCartDTO.orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "setAddressToCart - cart Id is null"));
        Cart cart = cartRepository.getOne(cartDTO.getId());
        DeliveryAddress deliveryAddress = deliveryAddressRepository.getOne(addressId);
        cart.setDeliveryAddress(deliveryAddress);
        cartRepository.save(cart);
        return CartMapper.toDto(cart);
    }

    public CartDTO closeCart(Long userId, Long deliveryAddressId) {
        if (userId == null) {
            throw new AppException(InfoCodes.SERVICE_CART, "closeCart - cart ID is null");
        }
        if (userId <= 0) {
            throw new AppException(InfoCodes.SERVICE_CART, "closeCart - cart ID less than zero");
        }
        if (deliveryAddressId == null) {
            throw new AppException(InfoCodes.SERVICE_CART, "closeCart - cart ID is null");
        }
        if (deliveryAddressId < 0) {
            throw new AppException(InfoCodes.SERVICE_CART, "closeCart - cart ID less than zero");
        }

        Optional<CartDTO> cartDTOOptional = getActiveCart(userId);
        CartDTO cartDTO = cartDTOOptional.orElseThrow(
                () -> new AppException(InfoCodes.SERVICE_CART, "closeCart - no cart for user with ID: " + userId));

        DeliveryAddress deliveryAddress = deliveryAddressRepository
                .findById(deliveryAddressId)
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "closeCart - no delivery address with ID: " + deliveryAddressId));

        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "closeCart - no user with ID: " + userId));

        Company company = companyRepository
                .findByUsers(user)
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "closeCart - no company for user with ID: " + user));

        Cart cart = cartRepository.getOne(cartDTO.getId());
        cart.setCartClosed(true);
        LocalDateTime purchaseTime = LocalDateTime.now();
        cart.setPurchaseTime(purchaseTime);
        String fileName = FileManager.generateFileName(cart.getUser().getCompany().getNip(), purchaseTime);
        cart.setOrderNumber(fileName.substring(0, fileName.length() - 4));
        cart.setDeliveryAddress(deliveryAddress);

        cartRepository.save(cart);

        // TODO: 09.09.2020
        String orderInXml = xmlParserOptima.generateXmlFileContent(cartDTO,
                cart.getProductsInCart().stream().map(ProductInCartMapper::toDto).collect(Collectors.toSet()),
                CompanyMapper.toDto(company));
        //String orderInXml = "ALALALALALA";
        // TODO: 13.08.2020  genrownie pliku z zamówieniem
        String pathToFile = FileManager.saveFileToDisk(orderInXml, fileName);
        emailService.sendEmail(CustomAddresses.DEFAULT_DESTINATION_MAILBOX, "ZAMÓWIENIE", fileName, pathToFile);
        // TODO: 15.09.2020 enable email service

        return CartMapper.toDto(cart);
    }

    public CartDTO closeCart(Long userId) {
        if (userId == null) {
            throw new AppException(InfoCodes.SERVICE_CART, "closeCart - cart ID is null");
        }
        if (userId <= 0) {
            throw new AppException(InfoCodes.SERVICE_CART, "closeCart - cart ID less than zero");
        }

        Optional<CartDTO> cartDTOOptional = getActiveCart(userId);
        CartDTO cartDTO = cartDTOOptional.orElseThrow(
                () -> new AppException(InfoCodes.SERVICE_CART, "closeCart - no cart for user with ID: " + userId));

        Cart cart = cartRepository.getOne(cartDTO.getId());
        cart.setCartClosed(true);
        LocalDateTime purchaseTime = LocalDateTime.now();
        cart.setPurchaseTime(purchaseTime);
        String fileName = FileManager.generateFileName(cartDTO.getUserDTO().getCompanyDTO().getNip(), purchaseTime);
        cart.setOrderNumber(fileName.substring(0, fileName.length() - 4));

        cartRepository.save(cart);

        // TODO: 09.09.2020
        // String orderInXml = xmlParserOptima.generateXmlFileContent(cartDTO, productsInCart);
        String orderInXml = "ALALALALALA";
        // TODO: 13.08.2020  genrownie pliku z zamówieniem
        String pathToFile = FileManager.saveFileToDisk(orderInXml, fileName);
        emailService.sendEmail(CustomAddresses.DEFAULT_DESTINATION_MAILBOX, "ZAMÓWIENIE", fileName, pathToFile);

        return CartMapper.toDto(cart);
    }

    public boolean userHasOpenCart(Long userId) {
        if (userId == null) {
            throw new AppException(InfoCodes.SERVICE_CART, "userHasOpenCart - cart ID is null");
        }
        if (userId <= 0) {
            throw new AppException(InfoCodes.SERVICE_CART, "userHasOpenCart - cart ID less than zero");
        }
        return Optional.of(getProductsOfActiveCart(userId)).isPresent();
    }
}