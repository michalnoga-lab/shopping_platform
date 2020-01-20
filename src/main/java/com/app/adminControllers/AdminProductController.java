package com.app.adminControllers;

import com.app.dto.ProductDTO;
import com.app.dto.ProductSearchDTO;
import com.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/products")
public class AdminProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("productSearch", new ProductSearchDTO());
        return "admin/products/all";
    }

    @PostMapping("/addCode/{id}")
    public String addCodePOST(@PathVariable Long id, @ModelAttribute ProductSearchDTO productSearchDTO, Model model) {

        // TODO: 2020-01-20 binding results

        productService.setCode(id, productSearchDTO.getUserInput());
        return "redirect:/"; // TODO: 2020-01-20 change
    }
}