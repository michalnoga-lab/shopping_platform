package com.app.infrastructure.security.service;

import com.app.domain.user.repository.UserRepository;
import com.app.infrastructure.security.exceptions.AppSecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User details service class
 */
@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new AppSecurityException("Username is null");
        }

        return userRepository
                .findByUsername(username)
                .map(user -> {
                    var authenticatedUser = user.toGetUserAuthentication();

                    return new User(
                            authenticatedUser.getEmail(),
                            authenticatedUser.getPassword(),
                            authenticatedUser.getEnabled(),
                            true,
                            true,
                            true,
                            List.of(new SimpleGrantedAuthority(authenticatedUser.getRole().toString()))
                    );
                })
                .orElseThrow(() -> new AppSecurityException("Cannot authenticate User"));
    }
}