package com.app.controllersRest;

import com.app.dto.CartDTO;
import com.app.dto.ProductDTO;
import com.app.service.CartService;
import com.app.service.CompanyService;
import com.app.service.ProductService;
import com.app.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductRestController {

    private final ProductService productService;
    private final CompanyService companyService;
    private final CartService cartService;

    private final SecurityService securityService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> all(HttpServletRequest request) {

        System.out.println("hit"); // TODO: 21.08.2020 remove

        return new ResponseEntity<>(productService.getProductsOfCompany(companyService.getCompanyOfUser(
                securityService.getLoggedInUserId(request.getAttribute("username").toString())).getId()
        ), HttpStatus.OK);
    }

    @PostMapping("/buy") // TODO: 13.08.2020 przesyłanie bez ID wszystko w body
    public ResponseEntity<CartDTO> buy(HttpServletRequest request, @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(cartService.addProductToCart(productDTO,
                securityService.getLoggedInUserId(request.getAttribute("username").toString())), HttpStatus.OK);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<CartDTO> remove(@PathVariable Long id, HttpServletRequest request) {
        return new ResponseEntity<>(cartService.removeProductFromCart(id,
                securityService.getLoggedInUserId(request.getAttribute("username").toString())), HttpStatus.OK);
    }
}