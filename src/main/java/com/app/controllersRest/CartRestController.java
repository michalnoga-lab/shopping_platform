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

    @GetMapping("all")
    public ResponseEntity<List<CartDTO>> all(HttpServletRequest request) {

        System.out.println("********************************");
        System.out.println(request.getAttribute("username")); // TODO: 30.03.2020 ---> null

        String username = (String) request.getAttribute("username");

        if (username == null) {
            // TODO: 31.03.2020 access denied
        }
        return new ResponseEntity<>(cartService.getAllUsersCarts(
                securityService.getLoggedInUserId(request.getAttribute("username").toString())), HttpStatus.OK);
    }

    @GetMapping("one/{id}")
    public ResponseEntity<CartDTO> one(@PathVariable Long id, HttpServletRequest request) {
        System.out.println(request.getAttribute("username"));
        return new ResponseEntity<>(cartService.getCart(id), HttpStatus.OK);
    }

    @GetMapping("one")
    public ResponseEntity<Set<ProductDTO>> activeOne(HttpServletRequest request) {
        Optional<CartDTO> cartDTOOptional = cartService.getActiveCart(
                securityService.getLoggedInUserId(request.getAttribute("username").toString()));
        if (cartDTOOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(productService.getProductsOfCart(cartDTOOptional.get().getId()), HttpStatus.OK);
    }

    @GetMapping("close")
    public ResponseEntity<CartDTO> closeActiveCart(HttpServletRequest request) {
        return new ResponseEntity<>(cartService.closeCart(
                securityService.getLoggedInUserId(request.getAttribute("username").toString())), HttpStatus.OK);
    }
}