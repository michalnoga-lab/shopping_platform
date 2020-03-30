package com.app.securityRest;

import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
import com.app.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getCompany().getNameShortcut() + "@" +
                            user.getName() + "." + user.getSurname(),
                    user.getPassword(),
                    Collections.emptyList()
            ));
        } catch (IOException e) {
            throw new AppException(InfoCodes.SECURITY, "attemptAuthentication - failed to login user: " + request.getUserPrincipal());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String roles = authResult.getAuthorities()
                .stream()
                .map(auth -> ((GrantedAuthority) auth).getAuthority()).collect(Collectors.joining());

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConfigConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConfigConstants.SECRET)
                .claim("roles", roles)
                .compact();

        response.addHeader(SecurityConfigConstants.HEADER_STRING, SecurityConfigConstants.TOKEN_PREFIX + token);

        response.getWriter().write(token);
        response.getWriter().flush();
        response.getWriter().close();
    }
}