package com.app.controllersRest;

import com.app.dto.DeliveryAddressDTO;
import com.app.mappers.DeliveryAddressMapper;
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
    private final SecurityService securityService;

    @GetMapping("/all")
    public ResponseEntity<List<DeliveryAddressDTO>> all() {
        // TODO: 31.03.2020 przekazać użytkownika
        return new ResponseEntity<>(deliveryAddressService.findAll(null), HttpStatus.OK);
    }

    @PostMapping("add")
    public ResponseEntity<DeliveryAddressDTO> add(@RequestBody DeliveryAddressDTO deliveryAddressDTO, HttpServletRequest request) {
        Long userId = securityService.getLoggedInUserId((String) request.getAttribute("username"));
        return new ResponseEntity<>(deliveryAddressService.add(deliveryAddressDTO, userId), HttpStatus.OK);
    }

    // TODO: 2020-03-24 remove
    // TODO: 2020-03-24 pick / select this one
}