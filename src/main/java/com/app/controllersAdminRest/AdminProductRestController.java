package com.app.controllersAdminRest;

import com.app.dto.CompanyDTO;
import com.app.dto.GeneralUserInputDTO;
import com.app.dto.ProductDTO;
import com.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/products")
public class AdminProductRestController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> allInDB() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<ProductDTO>> allProductsOfCompany(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductsOfCompany(id), HttpStatus.OK);
    }

    @PostMapping("/addCode/{id}")
    public ResponseEntity<ProductDTO> addCode(@PathVariable Long id, @ModelAttribute GeneralUserInputDTO generalUserInputDTO) {
        return new ResponseEntity<>(productService.setCode(id, generalUserInputDTO.getUserInput()), HttpStatus.OK);
    }

    @PostMapping("/removeCode/{id}")
    public ResponseEntity<ProductDTO> removeCode(@PathVariable Long id) {
        return new ResponseEntity<>(productService.removeCode(id), HttpStatus.OK);
    }

    @PostMapping("/hideAll/{id}")
    public ResponseEntity<CompanyDTO> hideAllProductsOfCompany(@PathVariable Long id) {
        return new ResponseEntity<>(productService.hideAllProductsOfCompany(id), HttpStatus.OK);
    }
}