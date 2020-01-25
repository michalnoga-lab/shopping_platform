package com.app.adminControllers;

import com.app.dto.CompanySearchDTO;
import com.app.service.FileService;
import com.app.service.ProductService;
import com.app.validators.CompanySearchDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class FileUploadController {

    private final FileService fileService;
    private final ProductService productService;

    private final CompanySearchDtoValidator companySearchDtoValidator;

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(companySearchDtoValidator);
    }

    @GetMapping("admin/products/add")
    public String handleFileUploadGET(Model model) {
        model.addAttribute("productSearch", new CompanySearchDTO());
        model.addAttribute("errors", new HashMap<>());
        return "admin/products/add";
    }

    @PostMapping("admin/products/add")
    public String handleFileUploadPOST(@RequestParam("file") MultipartFile file, @Valid @ModelAttribute CompanySearchDTO companySearchDTO,
                                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getCode));
            model.addAttribute("productSearch", new CompanySearchDTO());
            model.addAttribute("errors", errors);
            return "admin/products/add";
        }

        model.addAttribute("productSearch", companySearchDTO);
        productService.addProducts(fileService.getProductsFromFile(file, companySearchDTO.getUserIdInput()));
        return "admin/panel";
    }
}