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

        System.out.println("_______________________________________--"); // TODO: 12.06.2020
        System.out.println(request.getUserPrincipal());

        
        return new ResponseEntity<>(productService.findProductsOfCompany(companyService.getCompanyOfUser(
                securityService.getLoggedInUserId(request.getAttribute("username").toString())).getId()
        ), HttpStatus.OK);
    }

    @PostMapping("/buy/{id}")
    public ResponseEntity<CartDTO> buy(@RequestBody ProductDTO productDTO, HttpServletRequest request) {
        return new ResponseEntity<>(cartService.addProductToCart(productDTO,
                securityService.getLoggedInUserId(request.getAttribute("username").toString())), HttpStatus.OK);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<CartDTO> remove(@PathVariable Long id, HttpServletRequest request) {
        return new ResponseEntity<>(cartService.removeProductFromCart(id,
                securityService.getLoggedInUserId(request.getAttribute("username").toString())), HttpStatus.OK);
    }
}