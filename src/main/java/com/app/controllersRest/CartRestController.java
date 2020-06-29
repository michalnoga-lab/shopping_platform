package com.app.controllersRest;

import com.app.dto.CartDTO;
import com.app.dto.ProductDTO;
import com.app.service.CartService;
import com.app.service.ProductService;
import com.app.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/carts")
public class CartRestController {

    private final CartService cartService;
    private final ProductService productService;
    private final SecurityService securityService;

    @GetMapping("/all")
    public ResponseEntity<List<CartDTO>> all(HttpServletRequest request) {
        return new ResponseEntity<>(cartService.getAllUsersCarts(
                securityService.getLoggedInUserId(request.getAttribute("username").toString())), HttpStatus.OK);
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<CartDTO> one(@PathVariable Long id, HttpServletRequest request) {
        return new ResponseEntity<>(cartService.getCart(id), HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<Set<ProductDTO>> getActiveOne(HttpServletRequest request) {
        Optional<CartDTO> cartDTOOptional = cartService.getActiveCart(
                securityService.getLoggedInUserId(request.getAttribute("username").toString()));
        if (cartDTOOptional.isEmpty()) {
            return new ResponseEntity<>(Set.of(ProductDTO.builder().description("test_des_from_response").build()), HttpStatus.OK);
        }
        return new ResponseEntity<>(productService.getProductsOfCart(cartDTOOptional.get().getId()), HttpStatus.OK);
        // TODO: 24.06.2020 do poprawy - wracają produtky ostatniego otwartego koszyka
    }

    @GetMapping("/close")
    public ResponseEntity<CartDTO> closeActiveCart(HttpServletRequest request) {
        return new ResponseEntity<>(cartService.closeCart(
                securityService.getLoggedInUserId(request.getAttribute("username").toString())), HttpStatus.OK);
    }
}