package com.app.service;

import com.app.dto.CartDTO;
import com.app.dto.ProductDTO;
import com.app.dto.UserDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.mappers.CartMapper;
import com.app.mappers.ProductMapper;
import com.app.mappers.UserMapper;
import com.app.model.Cart;
import com.app.model.Product;
import com.app.model.User;
import com.app.repository.CartRepository;
import com.app.repository.ProductRepository;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartDTO addProductToCart(ProductDTO productDTO, Long userId) {
        if (productDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "addProductToCart - product is null");
        }

        // 1. pobrac usera po przekazanym id
        // 2. pobrac koszyk po id usera
        // 3. pobrac produkt po id produktu
        // 4. ustawic w koszyku produkt oraz usera (? tylko po co ustawiac usera skoro przed chwila koszyk pobrale na rzecz usera)
        // 5. ustawic inne dane dla zamowienia np ilosc
        // 6. za pomoca save zapisac koszyk z danymi

        User user = userRepository.getOne(userId);
        CartDTO cartDTO = getActiveCart(userId);
        Product product = productRepository.getOne(productDTO.getId());
        Cart cart = CartMapper.fromDto(cartDTO);
        Set<Product> productsInCart = cart.getProducts();

        if (productsInCart.contains(product)) {

            Product productAlreadyInCart = productsInCart
                    .stream()
                    .filter(prod -> prod.getId().equals(product.getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No product in cart with ID: " + product.getId()));

            productAlreadyInCart.setQuantity(productAlreadyInCart.getQuantity() + product.getQuantity());

        } else {
            productsInCart.add(product);
        }

        cart.setProducts(productsInCart);
        cart.setUser(user);
        cartRepository.save(cart);

        return CartMapper.toDto(cart); // TODO: 31.12.2019 czy robię bez zwracania koszyka ???

        /*CartDTO cartDTO = getUsersActiveCart(userDTO);
        Cart cart = CartMapper.fromDto(cartDTO);
        Product productToCart = ProductMapper.fromDto(productDTO);

        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx");
        System.out.println(productToCart);

        Product product = productRepository.findAll()
                .stream()
                .peek(pr -> {
                    System.out.println("**************************");
                    System.out.println(pr);
                })
                .filter(p -> p.getId().equals(productToCart.getId()))
                .findFirst()
                *//*.orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_CART,
                        "addProductToCart - no product with ID: " + productToCart.getId()));*//*
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_CART, "...."));

        cart.setProduct(product);
        cart.setUser(UserMapper.fromDto(userDTO));

        cartRepository.save(cart);

        return CartMapper.toDto(cart);*/
    }

    public List<CartDTO> getAllUsersCarts(Long userId) {
        if (userId == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "getAllUsersCarts - ID is null");
        }

        return cartRepository.findAllByUserId(userId)
                .stream()
                .map(CartMapper::toDto)
                .collect(Collectors.toList());

/*        return cartRepository
                .findAll()
                .stream()
                .filter(cart -> cart.getUser().getId().equals(userDTO.getId()))
                .map(CartMapper::toDto)
                .collect(Collectors.toList());*/ // TODO: 31.12.2019 remove
    }

    public CartDTO getActiveCart(Long userId) {
        if (userId == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "getOneCart - id is null");
        }
        if (userId < 0) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "getOneCart - id less than zero");
        }

        List<CartDTO> allUserCarts = getAllUsersCarts(userId);

        if (allUserCarts.size() > 0) {
            List<CartDTO> notClosedCarts = allUserCarts
                    .stream()
                    .filter(cartDTO -> cartDTO.getCartClosed().equals(false))
                    .collect(Collectors.toList());

            if (notClosedCarts.size() > 0) {
                return notClosedCarts.get(0);
            }
        }

        User user = userRepository.getOne(userId);
        return CartMapper.toDto(Cart.builder().user(user).build());

     /*   return cartRepository
                .findAll()
                .stream()
                .filter(cart -> cart.getId().equals(id))
                .map(CartMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_CART, "getOneCart - no cart with ID: " + id));*/
    }

    /*public CartDTO getUsersActiveCart(UserDTO userDTO) {
        if (userDTO == null) {
            throw new AppException(ExceptionCodes.SERVICE_CART, "getUsersActiveCart - user is null");
        }

        *//*Optional<Cart> cartOptional = cartRepository
                .findAll()
                .stream()
                .filter(cart -> cart.getUser().getId().equals(userDTO.getId()))
                .filter(cart -> cart.getCartClosed().equals(false))
                .findFirst();

        if (cartOptional.isEmpty()) {
            Optional<User> userOptional = userRepository
                    .findAll()
                    .stream()
                    .filter(user -> user.getLogin().equals(userDTO.getLogin()))
                    .findFirst(); // TODO: 2019-10-22 może wyszukiwanie po ID nie loginie ???

            if (userOptional.isPresent()) {
                Cart cart = Cart.builder().build();
                cart.setUser(userOptional.get());
                cartRepository.save(cart);
                return CartMapper.toDto(cart);
            }
        }
        return CartMapper.toDto(cartOptional.get());*//* // todo remove
    }*/
}