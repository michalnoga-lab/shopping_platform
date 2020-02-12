package com.app.service;

import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
import com.app.model.User;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final UserRepository userRepository;

    public Long getLoggedInUserId() {

        String username;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        /*User user = userRepository
                .findByLogin(username) // TODO: 12.02.2020 null jeżeli nie ma secuirty
                .orElseThrow(() -> new AppException(ExceptionCodes.SERVICE_SECURITY, "getLoggedInUser - no user with username: " + username));*/

        /*return user.getId();*/

        return 3L; // TODO: 09.01.2020  return real ID
    }
}