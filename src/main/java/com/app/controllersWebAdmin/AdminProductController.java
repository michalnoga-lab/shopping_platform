package com.app.controllersWebAdmin;

import com.app.dto.GeneralUserInputDTO;
import com.app.service.ProductService;
import com.app.validators.GeneralUserInputDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin/products")
public class AdminProductController {

    private final ProductService productService;

    private final GeneralUserInputDtoValidator generalUserInputDtoValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(generalUserInputDtoValidator);
    }

    @PostMapping("/all/{id}")
    public String allProductsOfCompany(@PathVariable Long id, Model model) {
        model.addAttribute("products", productService.getProductsOfCompany(id));
        model.addAttribute("generalUserInput", new GeneralUserInputDTO());
        model.addAttribute("errors", new HashMap<>());
        return "admin/products/all";
    }

    @GetMapping("/all")
    public String all(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("generalUserInput", new GeneralUserInputDTO());
        model.addAttribute("errors", new HashMap<>());
        return "admin/products/all";
    }

    @PostMapping("/addCode/{id}")
    public String addCode(@PathVariable Long id, @Valid @ModelAttribute GeneralUserInputDTO generalUserInputDTO,
                          BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode));
            model.addAttribute("products", productService.findAll());
            model.addAttribute("generalUserInput", new GeneralUserInputDTO());
            model.addAttribute("errors", errors);
            return "admin/products/all";
        }
        productService.setCode(id, generalUserInputDTO.getUserInput());
        return "redirect:/admin/products/all";
    }

    @PostMapping("/removeCode/{id}")
    public String removeCode(@PathVariable Long id) {
        productService.removeCode(id);
        return "redirect:/admin/products/all";
    }

    @PostMapping("/hideAll/{id}")
    public String hideAllProductsOfCompany(@PathVariable Long id) {
        productService.hideAllProductsOfCompany(id);
        return String.format("redirect:/admin/companies/one/%d", id);
    }
}