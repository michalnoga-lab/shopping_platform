package com.app.controllersAdminRest;

import com.app.dto.ProductCodeDTO;
import com.app.service.ProductCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public ResponseEntity<ProductCodeDTO> add(@RequestBody ProductCodeDTO productCodeDTO) {
        return new ResponseEntity<>(productCodeService.add(productCodeDTO), HttpStatus.CREATED);
    }
}