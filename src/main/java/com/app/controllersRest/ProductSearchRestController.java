package com.app.controllersRest;

import com.app.dto.ProductDTO;
import com.app.dto.ProductSearchDTO;
import com.app.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/search")
public class ProductSearchRestController {

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<List<ProductDTO>> search(@ModelAttribute ProductSearchDTO productSearchDTO) {
        return new ResponseEntity<>(productService.search(productSearchDTO), HttpStatus.OK);
    }
}