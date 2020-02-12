package com.app.security;

import com.app.exceptions.AppException;
import com.app.exceptions.ExceptionCodes;
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

        System.out.println("_____________1________________________________"); // TODO: 2020-02-09
        System.out.println("USERNAME=" + username);

        System.out.println("-------------------1.5-----------------------");

        try {
            if (username == null) {

                throw new NullPointerException("loadUserByUsername - login is null");
            }

            System.out.println("________2_____________________________________"); // TODO: 2020-02-09
            System.out.println(username);

            User user = userRepository.findByLogin(username)
                    .orElseThrow(() -> new UsernameNotFoundException(username)); // TODO: 2020-02-12 TU !!!


            System.out.println("_____________3________________________________"); // TODO: 2020-02-09
            System.out.println(user);


            return new org.springframework.security.core.userdetails.User(
                    user.getName(),
                    user.getPassword(),
                    user.getEnabled(),
                    true,
                    true,
                    true,
                    getAuthorities(user.getRole())
            );
        } catch (Exception e) {
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"); // TODO: 2020-02-12
            e.printStackTrace();
            throw new AppException(ExceptionCodes.SECURITY, "loadUserByUsername - no user with username: " + username);
        }
    }

    private Collection<GrantedAuthority> getAuthorities(Role role) {
        return Arrays.asList(new SimpleGrantedAuthority(role.getDescription()));
    }
}