package com.app.controllers;

import com.app.dto.CompanyDTO;
import com.app.repository.CompanyRepository;
import com.app.repository.ProductRepository;
import com.app.repository.UserRepository;
import com.app.service.CompanyService;
import com.app.service.ProductService;
import com.app.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final SecurityService securityService;
    private final ProductService productService;
    private final CompanyService companyService;

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("products", productService.getProductsOfCompany(
                companyService.getCompanyOfUser(securityService.getLoggedInUser())));
        return "/products/all";
    }

    @GetMapping("/one/{id}")
    public String one(@PathVariable Long id, Model model) {
        return "/products/one";
    }
}