package com.app.service;

import com.app.dto.UserDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.mappers.UserMapper;
import com.app.model.User;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepository;

    public UserDTO getLoggedInUser() {

        // TODO: 2019-09-23
        String login = "log";

        Optional<User> userOptional = userRepository
                .findAll()
                .stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
                /*.map(UserMapper::toDto)
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE, "getLoggedInUser - no user with login: " + login));*/

        //System.out.println(userOptional);

        return userOptional.isPresent() ? UserMapper.toDto(userOptional.get()) : UserDTO.builder().login("BLA").build();
    }
}