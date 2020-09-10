package com.app.service;

import com.app.Utilities.CustomAddresses;
import com.app.Utilities.FileManager;
import com.app.dto.CartDTO;
import com.app.dto.ProductDTO;
import com.app.dto.ProductsInCartDTO;
import com.app.dto.UserDTO;
import com.app.exceptions.AppException;
import com.app.mappers.CartMapper;
import com.app.mappers.ProductInCartMapper;
import com.app.model.*;
import com.app.parsers.XmlParserOptima;
import com.app.repository.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.source.spi.IdentifierSourceNonAggregatedComposite;
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
        }

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

        // TODO: 30.08.2020 metoda w repo
        Set<ProductsInCartDTO> productsInCartDTO = productsInCartRepository
                .findAll()
                .stream()
                .filter(prod -> prod.getCart().getId().equals(cart.getId()))
                .filter(prod -> prod.getHidden().equals(false))
                .map(ProductInCartMapper::toDto)
                .collect(Collectors.toSet());

        cartDTO.setProductsInCartDTO(productsInCartDTO);
        cartDTO.setUserDTO(UserDTO.builder().build());


        // TODO: 03.09.2020 nazwa produktu!

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

        // TODO: 27.08.2020
        // nie rob filtrowania tylko metode w repo
//        Cart cart = cartRepository.findAllByUserId(userId)
//                .stream()
//                .filter(c -> c.getCartClosed().equals(false))
//                .findFirst()
//                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "removeProductFromCart - no open cart for user with ID: " + userId));


        // TODO: 27.08.2020 metoda w repo
        Cart cart = cartRepository.findByUserId(userId)
                .stream()
                .filter(c -> c.getCartClosed().equals(false))
                .findFirst()
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "removeProductFromCart - no cart for user with ID: " + userId));


        // TODO: 27.08.2020
//        Long cartId = cartRepository.findByUserId(userId)
//                .stream()
//                .filter(c -> c.getCartClosed().equals(false))
//                .findFirst()
//                .get()
//                .getId();


        // TODO: 27.08.2020 metoda w repo
//        Optional<ProductsInCart> productsInCartToRemove = productsInCartRepository
//                .findAll()
//                .stream()
//                .filter(productsInCart -> productsInCart.getProductId().equals(productId))
//                .findFirst();
        //.orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "removeProductFromCart - no product with productID in cart: " + productId));

        // TODO: 27.08.2020
        // przeciez tutaj mozna robic takie pobranie produktow za pomoca metody w repo
//        Set<ProductsInCart> productsInCart = productsInCartRepository
//                .findAll()
//                .stream()
//                .filter(product -> product.getCart().getId().equals(cartId))
//                //        .filter(product -> !product.getProductId().equals(productId))
//                .collect(Collectors.toSet());


        //System.out.println(productsInCartToRemove);

        //productsInCartRepository.delete(productsInCartToRemove);

        // TODO: 27.08.2020 metoda w repo
        ProductsInCart productToRemove = productsInCartRepository
                .findAll()
                .stream()
                .filter(productsInCart -> productsInCart.getCart().getId().equals(cart.getId()))
                .filter(productsInCart -> productsInCart.getHidden().equals(false))
                .filter(productsInCart -> productsInCart.getProductId().equals(productId))
                .findFirst()
                .get();


        productToRemove.setHidden(true);
        productsInCartRepository.save(productToRemove);

        BigDecimal nettValue = calculateCartNettValue(productToRemove.getNettPrice(), productToRemove.getQuantity());
        BigDecimal vatValue = calculateCartVatValue(nettValue, productToRemove.getVat());
        BigDecimal grossValue = calculateTotalGrossValue(nettValue, vatValue);

        cart.setTotalNetValue(cart.getTotalNetValue().subtract(nettValue));
        cart.setTotalVatValue(cart.getTotalVatValue().subtract(vatValue));
        cart.setTotalGrossValue(cart.getTotalGrossValue().subtract(grossValue));


        //cart.setProductsInCart(productsInCart);
        cartRepository.save(cart);

        // TODO: 27.08.2020 zwracamy produkty aktualnie znajdujące się w koszyku


        //return CartMapper.toDto(cart);

        CartDTO cartDTO = CartMapper.toDto(cart);


        // TODO: 30.08.2020 metoda w repo
        Set<ProductsInCartDTO> productsInCarts = productsInCartRepository
                .findAll()
                .stream()
                .filter(prod -> prod.getCart().getId().equals(cart.getId()))
                .map(ProductInCartMapper::toDto)
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


    // TODO: 03.09.2020 getActiveCart - zwracamy cały koszyk
    public Optional<CartDTO> getActiveCart(Long userId) {
        if (userId == null) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getActiveCart - user ID is null");
        }
        if (userId < 0) {
            throw new AppException(InfoCodes.SERVICE_PRODUCT, "getActiveCart - cart ID less than zero");
        }

        // TODO: 01.09.2020 metoda w repo
        Optional<CartDTO> cartOptional = cartRepository
                .findByUserId(userId)
                .map(CartMapper::toDto);

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
                .findByUserId(userId);

        return cartInDB.map(cart -> cart.getProductsInCart()
                .stream()
                .map(ProductInCartMapper::toDto)
                .filter(product -> product.getHidden().equals(false))
                .collect(Collectors.toList()))
                .orElseGet(List::of);
    }

    // TODO: 31.08.2020
//    public Optional<CartDTO> getActiveCart(Long userId) {
//        if (userId == null) {
//            throw new AppException(InfoCodes.SERVICE_CART, "getActiveCart - ID is null");
//        }
//        if (userId <= 0) {
//            throw new AppException(InfoCodes.SERVICE_CART, "getActiveCart - ID less than zero");
//        }
//        List<CartDTO> allUserCarts = getAllUsersCarts(userId);
//
//        if (allUserCarts.size() > 0) {
//            List<CartDTO> notClosedCarts = allUserCarts
//                    .stream()
//                    .filter(cartDTO -> cartDTO.getCartClosed().equals(false))
//                    .collect(Collectors.toList());
//
//            if (notClosedCarts.size() > 0) {
//                return Optional.of(notClosedCarts.get(0));
//            }
//        }
//        return Optional.empty();
//    }

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

//    public CartDTO closeCart(Long userId, Long cartID) {
//
//        System.out.println("----------------------------- 1"); // TODO: 09.09.2020
//        System.out.println(userId);
//        System.out.println(cartID);
//
//        if (userId == null) {
//            throw new AppException(InfoCodes.SERVICE_CART, "closeCart - cart ID is null");
//        }
//        if (userId <= 0) {
//            throw new AppException(InfoCodes.SERVICE_CART, "closeCart - cart ID less than zero");
//        }
//
//        Optional<CartDTO> cartDTOOptional = getActiveCart(userId);
//        CartDTO cartDTO = cartDTOOptional.orElseThrow(
//                () -> new AppException(InfoCodes.SERVICE_CART, "closeCart - no cart for user with ID: " + userId));
//        DeliveryAddress deliveryAddress = deliveryAddressRepository
//                .findById(cartID)
//                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_CART, "closeCart - no delivery address with ID: " + cartID));
//
//        Cart cart = cartRepository.getOne(cartDTO.getId());
//        cart.setCartClosed(true);
//        LocalDateTime purchaseTime = LocalDateTime.now();
//        cart.setPurchaseTime(purchaseTime);
//
//        // TODO: 09.09.2020 get real NIP
////        String fileName = FileManager.generateFileName(cartDTO.getUserDTO().getCompanyDTO().getNip(), purchaseTime);
//        String fileName = FileManager.generateFileName("000000000", purchaseTime);
//
//        cart.setOrderNumber(fileName.substring(0, fileName.length() - 4));
//        cart.setDeliveryAddress(deliveryAddress);
//
//        cartRepository.save(cart);
//
//        //String orderInXml = xmlParserOptima.generateXmlFileContent(cartDTO, productsInCart);
//        String orderInXml = "fjsdfjsdfkdsfkdskhkdsh";
//        // TODO: 13.08.2020  genrownie pliku z zamówieniem
//        String pathToFile = FileManager.saveFileToDisk(orderInXml, fileName);
//        emailService.sendEmail(CustomAddresses.DEFAULT_DESTINATION_MAILBOX, "ZAMÓWIENIE", fileName, pathToFile);
//
//        return CartMapper.toDto(cart);
//        //return CartDTO.builder().build();
//    }

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

        Cart cart = cartRepository.getOne(cartDTO.getId());
        cart.setCartClosed(true);
        LocalDateTime purchaseTime = LocalDateTime.now();
        cart.setPurchaseTime(purchaseTime);
        String fileName = FileManager.generateFileName(cart.getUser().getCompany().getNip(), purchaseTime);
        cart.setOrderNumber(fileName);
        cart.setDeliveryAddress(deliveryAddress);

        cartRepository.save(cart);

        // TODO: 09.09.2020
        // String orderInXml = xmlParserOptima.generateXmlFileContent(cartDTO, productsInCart);
        String orderInXml = "ALALALALALA";
        // TODO: 13.08.2020  genrownie pliku z zamówieniem
        String pathToFile = FileManager.saveFileToDisk(orderInXml, fileName);
        emailService.sendEmail(CustomAddresses.DEFAULT_DESTINATION_MAILBOX, "ZAMÓWIENIE", fileName, pathToFile);

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
        cart.setOrderNumber(fileName);

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

        // TODO: 31.08.2020 do poprawy return statement 
        return Optional.of(getProductsOfActiveCart(userId)).isPresent();
    }
}