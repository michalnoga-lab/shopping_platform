package com.app.controllersAdminRest;

import com.app.dto.UserDTO;
import com.app.model.Role;
import com.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin/users")
public class AdminUserRestController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserDTO> add(@ModelAttribute UserDTO userDTO) {
        return new ResponseEntity<>(userService.addUser(userDTO, Role.USER), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> all() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<UserDTO>> allOfCompany(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findAllOfCompany(id), HttpStatus.OK);
    }

    @GetMapping("/one/{id}")
    public ResponseEntity<UserDTO> one(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/disable/{id}")
    public ResponseEntity<UserDTO> disable(@PathVariable Long id) {
        return new ResponseEntity<>(userService.disableEnable(id, false), HttpStatus.OK);
    }

    @PostMapping("/enable/{id}")
    public ResponseEntity<UserDTO> enable(@PathVariable Long id) {
        return new ResponseEntity<>(userService.disableEnable(id, true), HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<UserDTO> edit(@ModelAttribute UserDTO userDTO) {
        return new ResponseEntity<>(userService.update(userDTO), HttpStatus.OK);
    }
}