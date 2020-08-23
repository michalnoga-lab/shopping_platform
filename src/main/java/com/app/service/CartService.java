package com.app.service;

import com.app.Utilities.CustomAddresses;
import com.app.Utilities.FileManager;
import com.app.dto.CartDTO;
import com.app.dto.ProductDTO;
import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
import com.app.mappers.CartMapper;
import com.app.model.*;
import com.app.parsers.XmlParserOptima;
import com.app.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final ProductsInCartRepository productsInCartRepository;


    private final XmlParserOptima xmlParserOptima;
    private final EmailService emailService;


    // TODO: 23.08.2020 remove
//    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository, DeliveryAddressRepository deliveryAddressRepository, ProductsInCartRepository productsInCartRepository, XmlParserOptima xmlParserOptima, EmailService emailService) {
//        this.cartRepository = cartRepository;
//        this.userRepository = userRepository;
//        this.productRepository = productRepository;
//        this.deliveryAddressRepository = deliveryAddressRepository;
//        this.productsInCartRepository = productsInCartRepository;
//        this.xmlParserOptima = xmlParserOptima;
//        this.emailService = emailService;
//    }
    // TODO: 21.08.2020 remove

    public CartDTO getCart(Long cartId) {
        if (cartId == null) {
            throw new AppException(InfoCodes.SERVICE_CART, "getCart - cart ID is null");
        }
        if (cartId <= 0) {
            throw new AppException(InfoCodes.SERVICE_CART, "getCart - cart ID less than zero");
        }

        return cartRepository.findById(cartId)
                .stream()
                .map(CartMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "getCart - no cart with ID: " + cartId));
    }

    public CartDTO addProductToCart(ProductDTO productDTO, Long userId) {
        if (productDTO == null) {
            throw new AppException(InfoCodes.SERVICE_CART, "addProductToCart - product is null");
        }

        // TODO: 13.08.2020
        System.out.println(productDTO);


        User user = userRepository.getOne(userId);
        Optional<CartDTO> cartDTOOptional = getActiveCart(userId);

        Product product = productRepository.getOne(productDTO.getId());

        //product.setQuantity(product.getQuantity() + productDTO.getQuantity());
        // TODO: 13.08.2020

        Cart cart;
        Optional<Cart> cartOptional = Optional.empty();

        if (cartDTOOptional.isPresent()) {
            cartOptional = cartRepository.findById(cartDTOOptional.get().getId());
        }

        cart = cartOptional.orElseGet(() -> Cart.builder().cartClosed(false).build());
        // TODO: 13.08.2020 domyślne wartości startowe koszyka netto vat brutto ???

        //Set<Product> productsInCart = new HashSet<>();

        //Set<ProductsInCart> productsInCart = getAllProductsFromCart(cart.getId());

        ProductsInCart singleProductInCart = ProductsInCart.builder()
                .cart(cart)
                .productId(product.getId())
                .quantity(productDTO.getQuantity())
                .nettPrice(product.getNettPrice())
                .vat(product.getVat())
                .grossPrice(product.getGrossPrice())
                .build();

        //productsInCart.add(singleProductInCart);

        /*if (cart.getProducts() != null) {
            productsInCart = cart.getProducts();
        }*/
        // TODO: 13.08.2020

        //cart.setProducts(productsInCart);
        // TODO: 13.08.2020

//        CartDTO cartValues = calculateCartValue(productsInCart, user.getCompany().getDefaultPrice());
//        cart.setTotalNetValue(cartValues.getTotalNetValue());
//        cart.setTotalVatValue(cartValues.getTotalVatValue());
//        cart.setTotalGrossValue(cartValues.getTotalGrossValue());

        System.out.println("ble1");

        BigDecimal totalNettCartValue = calculateCartNettValue
                (singleProductInCart.getNettPrice(),
                        singleProductInCart.getQuantity());

        BigDecimal totalVatCartValue = calculateCartVatValue(totalNettCartValue, product.getVat());

        BigDecimal totalGrossCartValue = calculateTotalGrossValue(totalNettCartValue, totalVatCartValue);

        System.out.println("ble2");
        System.out.println(totalNettCartValue);
        System.out.println(totalVatCartValue);

        cart.setTotalNetValue(totalNettCartValue);
        cart.setTotalVatValue(totalVatCartValue);
        cart.setTotalGrossValue(totalGrossCartValue);

        productsInCartRepository.save(singleProductInCart);
        cart.setUser(user);
        cartRepository.save(cart);
        return CartMapper.toDto(cart);
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

    public List<ProductsInCart> getAllProductsFromCart(Long cartId) {
        return List.of(); // TODO: 13.08.2020
    }

    public CartDTO removeProductFromCart(Long productId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "removeProductFromCart - no user with ID: " + userId));

        Cart cart = cartRepository.findAllByUserId(userId)
                .stream()
                .filter(c -> c.getCartClosed().equals(false))
                .findFirst()
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "removeProductFromCart - no open cart for user with ID: " + userId));

//        Set<Product> products = cart.getProducts()
//                .stream()
//                .filter(product -> !product.getId().equals(productId))
//                .collect(Collectors.toSet());
        Set<Product> products = new HashSet<>();
        // TODO: 13.08.2020

        //cart.setProducts(products);

//        CartDTO cartValues = calculateCartValue(products, user.getCompany().getDefaultPrice());
//        cart.setTotalNetValue(cartValues.getTotalNetValue());
//        cart.setTotalVatValue(cartValues.getTotalVatValue());
//        cart.setTotalGrossValue(cartValues.getTotalGrossValue());


        // TODO: 13.08.2020 do poprawy wartość koszyka

        cartRepository.save(cart);
        return CartMapper.toDto(cart);
    }

    public CartDTO calculateCartValue(Set<ProductsInCart> productsInCart, Price priceType) {
        if (productsInCart == null) {
            throw new AppException(InfoCodes.SERVICE_CART, "calculateCartValue - products set is null");
        }
        CartDTO cartDTO = new CartDTO();
        BigDecimal totalNetValue = BigDecimal.ZERO;
        BigDecimal totalVatValue = BigDecimal.ZERO;
        BigDecimal totalGrossValue = BigDecimal.ZERO;

        // TODO: 13.08.2020 do poprawy
//        if (priceType.equals(Price.NET)) {
//            for (Product product : productsInCart) {
//                BigDecimal currentNetValue = product.getNettPrice().multiply(BigDecimal.valueOf(product.getQuantity()));
//                BigDecimal currentVatValue = currentNetValue.multiply(BigDecimal.valueOf(product.getVat()));
//                BigDecimal currentGrossValue = currentNetValue.add(currentVatValue);
//
//                totalNetValue = totalNetValue.add(currentNetValue);
//                totalVatValue = totalVatValue.add(currentVatValue);
//                totalGrossValue = totalGrossValue.add(currentGrossValue);
//            }
//        } else {
//            for (Product product : productsInCart) {
//                BigDecimal currentGrossValue = product.getGrossPrice().multiply(BigDecimal.valueOf(product.getQuantity()));
//                BigDecimal currentNetValue = currentGrossValue.divide(
//                        (BigDecimal.ONE.setScale(2, RoundingMode.HALF_UP).add(BigDecimal.valueOf(
//                                product.getVat()).setScale(2, RoundingMode.HALF_UP))));
//                BigDecimal currentVatValue = currentGrossValue.subtract(currentNetValue);
//
//                totalGrossValue = totalGrossValue.add(currentGrossValue);
//                totalVatValue = totalVatValue.add(currentVatValue);
//                totalNetValue = totalNetValue.add(currentNetValue);
//            }
//        }
//        cartDTO.setTotalNetValue(totalNetValue.setScale(2, RoundingMode.HALF_UP));
//        cartDTO.setTotalVatValue(totalVatValue.setScale(2, RoundingMode.HALF_UP));
//        cartDTO.setTotalGrossValue(totalGrossValue.setScale(2, RoundingMode.HALF_UP));
        return cartDTO;
    }

    public List<CartDTO> getAllUsersCarts(Long userId) {
        if (userId == null) {
            throw new AppException(InfoCodes.SERVICE_CART, "getAllUsersCarts - ID is null");
        }
        List<CartDTO> usersCarts = new ArrayList<>();

        cartRepository.findAllByUserId(userId)
                .stream()
                .map(CartMapper::toDto)
                .forEach(usersCarts::add);

        return usersCarts;
    }

    public Optional<CartDTO> getActiveCart(Long userId) {
        if (userId == null) {
            throw new AppException(InfoCodes.SERVICE_CART, "getActiveCart - ID is null");
        }
        if (userId <= 0) {
            throw new AppException(InfoCodes.SERVICE_CART, "getActiveCart - ID less than zero");
        }
        List<CartDTO> allUserCarts = getAllUsersCarts(userId);

        if (allUserCarts.size() > 0) {
            List<CartDTO> notClosedCarts = allUserCarts
                    .stream()
                    .filter(cartDTO -> cartDTO.getCartClosed().equals(false))
                    .collect(Collectors.toList());

            if (notClosedCarts.size() > 0) {
                return Optional.of(notClosedCarts.get(0));
            }
        }
        return Optional.empty();
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

    public CartDTO closeCart(Long userId) {
        if (userId == null) {
            throw new AppException(InfoCodes.SERVICE_CART, "closeCart - cart ID is null");
        }
        if (userId <= 0) {
            throw new AppException(InfoCodes.SERVICE_CART, "closeCart - cart ID less than zero");
        }

        Optional<CartDTO> cartDTOOptional = getActiveCart(userId);
        CartDTO cartDTO = cartDTOOptional.orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "closeCart - no cart for user with ID: " + userId));
        Cart cart = cartRepository.getOne(cartDTO.getId());
        cart.setCartClosed(true);
        cart.setPurchaseTime(LocalDateTime.now());
        String fileName = FileManager.generateFileName(cartDTO.getUserDTO().getCompanyDTO().getNip());
        cart.setOrderNumber(fileName);

        cartRepository.save(cart);
//        Set<ProductDTO> productsInCart = cart.getProducts()
//                .stream()
//                .map(ProductMapper::toDto)
//                .collect(Collectors.toSet());

        //String orderInXml = xmlParserOptima.generateXmlFileContent(cartDTO, productsInCart);
        String orderInXml = "";
        // TODO: 13.08.2020  
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
        return getActiveCart(userId).isPresent();
    }
}