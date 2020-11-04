package com.app.controllersAdminRest;

import com.app.dto.ProductCodeDTO;
import com.app.service.ProductCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/productCodes")
public class ProductCodeController {

    private final ProductCodeService productCodeService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductCodeDTO>> all() {
        return new ResponseEntity<>(productCodeService.findAll(), HttpStatus.OK);
    }

    // add

    // remove
}