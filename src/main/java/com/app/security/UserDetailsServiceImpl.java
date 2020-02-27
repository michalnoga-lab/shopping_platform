package com.app.security;

import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
import com.app.model.Role;
import com.app.model.User;
import com.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Qualifier("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            if (username == null) {

                throw new NullPointerException("loadUserByUsername - login is null");
            }

            User user = userRepository.findByLogin(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username));

            return new org.springframework.security.core.userdetails.User(
                    user.getCompany().getNameShortcut() + "@" +
                            user.getName() + "." + user.getSurname(),
                    user.getPassword(),
                    user.getEnabled(),
                    true,
                    true,
                    true,
                    getAuthorities(user.getRole())
            );

        } catch (Exception e) {
            throw new AppException(InfoCodes.SECURITY, "loadUserByUsername - failed to login user: " + username);
        }
    }

    private Collection<GrantedAuthority> getAuthorities(Role role) {
        return Arrays.asList(new SimpleGrantedAuthority(role.getDescription()));
    }
}