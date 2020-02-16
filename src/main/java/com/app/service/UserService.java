package com.app.service;

import com.app.dto.UserDTO;
import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
import com.app.mappers.UserMapper;
import com.app.model.Role;
import com.app.model.User;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO findUserByLogin(String login) {
        if (login == null || login.length() == 0) {
            throw new AppException(InfoCodes.SERVICE_USER, "findUserByLogin - login is null");
        }
        return userRepository
                .findAll()
                .stream()
                .filter(user -> user.getLogin().equals(login))
                .map(UserMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_USER, "findUserByLogin - no user with login:" + login));
    }

    public UserDTO addUser(UserDTO userDTO, Role role) {
        if (userDTO == null) {
            throw new AppException(InfoCodes.SERVICE_USER, "addUser - user is null");
        }
        User user = UserMapper.fromDto(userDTO);
        user.setLogin(userDTO.getName() + "." + userDTO.getSurname());
        user.setEnabled(true);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(user);
        return UserMapper.toDto(user);
    }

    public List<UserDTO> findAll() {
        List<User> users = new ArrayList<>(userRepository.findAll());

        return users
                .stream()
                .filter(user -> user.getRole().equals(Role.USER))
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO findById(Long id) {
        if (id == null) {
            throw new AppException(InfoCodes.SERVICE_USER, "findById - ID is null");
        }
        if (id < 0) {
            throw new AppException(InfoCodes.SERVICE_USER, "findById - ID less than zero");
        }
        return userRepository
                .findById(id)
                .stream()
                .map(UserMapper::toDto)
                .findFirst()
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_USER, "findById - no user with ID: " + id));
    }

    public UserDTO disableEnable(Long userId, Boolean enabled) {
        if (userId == null) {
            throw new AppException(InfoCodes.SERVICE_COMPANY, "disableEnable - user ID is null");
        }
        if (userId <= 0) {
            throw new AppException(InfoCodes.SERVICE_COMPANY, "disableEnable - user ID less than zero");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_USER, "disableEnable - no user with ID: " + userId));
        user.setEnabled(enabled);
        userRepository.save(user);
        return UserMapper.toDto(user);
    }

    public UserDTO update(UserDTO userDTO) {

        if (userDTO == null) {
            throw new AppException(InfoCodes.SERVICE_USER, "update - userDTO is null");
        }

        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new AppException(InfoCodes.SERVICE_USER, "update - no user in DB with ID: " + userDTO.getId()));

        user.setName(userDTO.getName() == null ? user.getName() : userDTO.getName());
        user.setSurname(userDTO.getSurname() == null ? user.getSurname() : userDTO.getSurname());
        user.setEmail(userDTO.getEmail() == null ? user.getEmail() : userDTO.getEmail());
        user.setPassword(userDTO.getPassword() == null ? user.getPassword() : userDTO.getPassword());
        user.setPasswordConfirmation(userDTO.getPasswordConfirmation() == null ?
                user.getPasswordConfirmation() : userDTO.getPasswordConfirmation());

        userRepository.save(user);
        return UserMapper.toDto(user);
    }
}