package com.app.service;

import com.app.dto.UserDTO;
import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.mappers.UserMapper;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO findUserByLogin(String login) {
        if (login == null || login.length() == 0) {
            throw new AppException(ExceptionCodes.SERVICE, "findUserByLogin - login is null");
        }

        return userRepository
                .findAll()
                .stream()
                .filter(user -> user.getLogin().equals(login))
                .map(UserMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE, "findUserByLogin - no user with login:" + login));
    }
}