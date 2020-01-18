package com.app.adminControllers;

import com.app.dto.CompanyDTO;
import com.app.service.FileService;
import com.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class FileUploadController {

    private final FileService fileService;
    private final ProductService productService;

    @GetMapping("admin/products/add")
    public String handleFileUploadGET(Model model) {
        model.addAttribute("company", new CompanyDTO());
        return "admin/products/add";
    }

    @PostMapping("admin/products/add")
    public String handleFileUploadPOST(@RequestParam("file") MultipartFile file,
                                       @ModelAttribute CompanyDTO companyDTO, Model model) {
        model.addAttribute("company", companyDTO); // TODO: 2020-01-18 search instate of company
        productService.addProducts(fileService.getProductsFromFile(file));
        return "admin/panel";
    }
}