package com.app.controllers;

import com.app.dto.ProductDTO;
import com.app.service.CartService;
import com.app.service.CompanyService;
import com.app.service.ProductService;
import com.app.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final SecurityService securityService;
    private final ProductService productService;
    private final CompanyService companyService;
    private final CartService cartService;

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("products", productService.getProductsOfCompany(
                companyService.getCompanyOfUser(securityService.getLoggedInUser())));
        return "/products/all";
    }

    @GetMapping("/one/{id}")
    public String one(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getOneProduct(id));
        return "/products/one";
    }

    @PostMapping("/one/{id}")
    public String buy(@ModelAttribute ProductDTO productDTO, Model model){
        model.addAttribute("product", new ProductDTO());
        cartService.addProductToCart(productDTO, securityService.getLoggedInUser());
        return "/products/added";
    }

    @PostMapping("/buy")
    public String buyPOST(@ModelAttribute ProductDTO productDTO, Model model) {
        model.addAttribute("product", new ProductDTO());
        cartService.addProductToCart(productDTO, securityService.getLoggedInUser());
        return "/products/added";
    }

    @GetMapping("/buy")
    public String buyGET(Model model) {
        model.addAttribute("product", new ProductDTO());
        return "/products/added";
    }
}