package com.app.application.service;

import com.app.domain.user.dto.CreateUserDto;
import com.app.domain.user.dto.CreateUserResponseDto;
import com.app.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UserRepository userRepository;

    public CreateUserResponseDto createUser(CreateUserDto createUserDto) {


        // TODO
        return CreateUserResponseDto.builder().build();
    }

}