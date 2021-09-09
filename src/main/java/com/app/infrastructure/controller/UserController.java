package com.app.infrastructure.controller;

import com.app.application.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Users
 * Allows User registration and activation
 *
 * @see com.app.domain.user.User
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UsersService usersService;

    /**
     * Registers User and saves to DB
     *
     * @param createUserDto User data provided to register in JSON format
     * @return ResponseDataDto
     * @see com.app.domain.user.User
     */
//    @PostMapping("/register")
//    public ResponseDataDto<Long> register(@RequestBody CreateUserDto createUserDto) {
//        return toResponse(usersService.createUser(createUserDto));
//    }

    // TODO - security -> do jakich adresów ma mieć jaki user dostęp
}