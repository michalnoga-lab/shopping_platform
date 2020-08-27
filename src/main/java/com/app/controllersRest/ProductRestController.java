package com.app.controllersRest;

import com.app.dto.CartDTO;
import com.app.dto.ProductDTO;
import com.app.dto.ProductsInCartDTO;
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
        return new ResponseEntity<>(productService.getProductsOfCompany(companyService.getCompanyOfUser(
                securityService.getLoggedInUserId(request.getAttribute("username").toString())).getId()
        ), HttpStatus.OK);
    }

    @PostMapping("/buy")
    public ResponseEntity<CartDTO> buy(HttpServletRequest request, @RequestBody ProductDTO productDTO) {
        return new ResponseEntity<>(cartService.addProductToCart(productDTO,
                securityService.getLoggedInUserId(request.getAttribute("username").toString())), HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<CartDTO> remove(HttpServletRequest request, @RequestBody ProductsInCartDTO productsInCartDTO) {

        // TODO: 26.08.2020 remove
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println(productsInCartDTO);

        return new ResponseEntity<>(cartService.removeProductFromCart(productsInCartDTO.getProductId(),
                securityService.getLoggedInUserId("user@gmail.com")), HttpStatus.OK);
    }
}