package com.app.controllersRest;

import com.app.dto.CartDTO;
import com.app.dto.DeliveryAddressDTO;
import com.app.service.CartService;
import com.app.service.DeliveryAddressService;
import com.app.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/deliveryAddress")
public class DeliveryAddressRestController {

    private final DeliveryAddressService deliveryAddressService;
    private final CartService cartService;
    private final SecurityService securityService;

    @GetMapping("/all")
    public ResponseEntity<List<DeliveryAddressDTO>> all(HttpServletRequest request) {
        return new ResponseEntity<>(deliveryAddressService.findAll(securityService.getLoggedInUserId(
                request.getAttribute("username").toString())), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<DeliveryAddressDTO> add(@RequestBody DeliveryAddressDTO deliveryAddressDTO, HttpServletRequest request) {
        return new ResponseEntity<>(deliveryAddressService.add(deliveryAddressDTO,
                securityService.getLoggedInUserId(request.getAttribute("username").toString())), HttpStatus.OK);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity<DeliveryAddressDTO> remove(@PathVariable Long id) {
        return new ResponseEntity<>(deliveryAddressService.hide(id), HttpStatus.OK);
    }

    @GetMapping("/pick/{id}")
    public ResponseEntity<CartDTO> pick(@PathVariable Long id, HttpServletRequest request) {
        return new ResponseEntity<>(cartService.setAddressToCart(id,
                securityService.getLoggedInUserId(request.getAttribute("username").toString())), HttpStatus.OK);
    }
}