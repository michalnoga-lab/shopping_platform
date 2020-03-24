package com.app.controllersRest;

import com.app.dto.CartDTO;
import com.app.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/carts")
public class CartRestController {

    private final CartService cartService;

    @GetMapping("all")
    public ResponseEntity<List<CartDTO>> all() {
        return new ResponseEntity<>(cartService.getAllUsersCarts(null), HttpStatus.OK);
        // TODO: 24.03.2020 jak przekazać użytkownika ???
    }
}