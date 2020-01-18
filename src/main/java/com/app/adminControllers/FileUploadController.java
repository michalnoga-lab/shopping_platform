package com.app.adminControllers;

import com.app.service.FileService;
import com.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class FileUploadController {

    private final FileService fileService;
    private final ProductService productService;

    @PostMapping("admin/products/add")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        productService.addProducts(fileService.getProductsFromFile(file));
        return "admin/panel";
    }
}