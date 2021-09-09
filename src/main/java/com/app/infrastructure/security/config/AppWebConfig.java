package com.app.infrastructure.security.config;

import com.app.infrastructure.security.dto.ErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@EnableWebSecurity
@RequiredArgsConstructor
public class AppWebConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors()

                .and()
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler())

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/", "/users/register", "/users/activation", "/security/**").permitAll()
                .antMatchers().hasAnyRole("ADMIN") // TODO
                .antMatchers().hasAnyRole("USER") // TODO
                .anyRequest().authenticated()

                .and();
                //.addFilter(new AppAuthenticationFilter(authenticationManager(), appTokenService)) TODO
                //.addFilter(new AppAuthorizationFilter(authenticationManager(), appTokensService)); TODO

    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return ((httpServletRequest, httpServletResponse, e) -> {
            var error = ErrorDto.builder().message(e.getMessage()).build();

            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(error));
            httpServletResponse.getWriter().flush();
            httpServletResponse.getWriter().close();
        });
    }

    private AccessDeniedHandler accessDeniedHandler() {
        return (httpServletRequest, httpServletResponse, e) -> {
            var error = ErrorDto.builder().message(e.getMessage()).build();

            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(error));
            httpServletResponse.getWriter().flush();
            httpServletResponse.getWriter().close();
        };
    }


}