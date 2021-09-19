package com.app.infrastructure.security.filters;

import com.app.infrastructure.security.dto.AuthenticationDto;
import com.app.infrastructure.security.exceptions.AppSecurityException;
import com.app.infrastructure.security.tokens.AppTokensService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * App authentication filter
 */
@RequiredArgsConstructor
public class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AppTokensService appTokensService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            var userToAuthenticate = new ObjectMapper().readValue(request.getInputStream(), AuthenticationDto.class);

            return authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            userToAuthenticate.getEmail(),
                            userToAuthenticate.getPassword(),
                            Collections.emptyList()
                    ));
        } catch (Exception e) {
            throw new AppSecurityException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException {

        var tokens = appTokensService.generateTokens(authResult);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(tokens));
        response.getWriter().flush();
        response.getWriter().close();
    }
}