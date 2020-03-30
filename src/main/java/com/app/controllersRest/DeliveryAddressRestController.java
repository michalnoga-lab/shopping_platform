package com.app.controllersRest;

import com.app.dto.DeliveryAddressDTO;
import com.app.mappers.DeliveryAddressMapper;
import com.app.service.DeliveryAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/deliveryAddress")
public class DeliveryAddressRestController {

    private final DeliveryAddressService deliveryAddressService;

    @GetMapping("/all")
    public ResponseEntity<List<DeliveryAddressDTO>> all() {
        return new ResponseEntity<>(deliveryAddressService.findAll(null), HttpStatus.OK);
        // TODO: 2020-03-24 jak przekazać użytkownika ???
    }

    // TODO: 2020-03-24 add
    // TODO: 2020-03-24 remove
    // TODO: 2020-03-24 pick / select this one
}