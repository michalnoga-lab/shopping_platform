package com.app.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class CustomWebSecurity extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    public CustomWebSecurity(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
/*
    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/resources/static/css/**").anyRequest();
    }*/ // TODO: 2020-02-09 wyłącza security

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeRequests()
                /*.antMatchers("/mange/**", "/webjars/**", "/").permitAll()*/ // TODO: 2020-02-09   tak było
                .antMatchers("/manage/**", "/webjars/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/templates/index.html").permitAll()
                .antMatchers("/templates/fragments/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/resources/css/*.css").permitAll()
                .antMatchers("/templates/carts/**",
                        "/templates/deliveryAddress/**",
                        "/templates/products/**",
                        "/templates/search/**",
                        "/templates/security/**").hasAnyRole("USER", "ADMIN", "SUPER")
                .antMatchers("/admin/**").permitAll() // TODO: 2020-02-09 tylko admin i super

                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/security/login").permitAll()
                .loginProcessingUrl("/app-login")
                .usernameParameter("login")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/security/failed")

                .and()
                .logout().permitAll()
                .logoutUrl("/app-logout")
                .clearAuthentication(true)
                .logoutSuccessUrl("/security/loggedOut")

                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest httpServletRequest,
                               HttpServletResponse httpServletResponse,
                               AccessDeniedException e) throws IOException, ServletException {
                httpServletResponse.sendRedirect("/accessDenied");
            }
        };
    }
}