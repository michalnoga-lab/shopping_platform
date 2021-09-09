package com.app.infrastructure.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@RequiredArgsConstructor
public class AppWebConfig {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

    }
}