package com.app.securityRest;

import com.app.exceptions.AppException;
import com.app.model.InfoCodes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(SecurityConfigConstants.HEADER_STRING);
        if (header == null || !header.startsWith(SecurityConfigConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) {

        String token = request.getHeader(SecurityConfigConstants.HEADER_STRING);

        try {
            if (token != null) {
                Claims claims = Jwts.parser()
                        .setSigningKey(SecurityConfigConstants.SECRET)
                        .parseClaimsJws(token.replace(SecurityConfigConstants.TOKEN_PREFIX, ""))
                        .getBody();

                String username = claims.getSubject();
                List<GrantedAuthority> roles = Arrays
                        .stream(claims.get("roles", String.class).split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

                if (username == null || roles == null) {
                    throw new AppException(InfoCodes.SECURITY, "getAuthentication - username or role is null for user: " + username);
                }
                return new UsernamePasswordAuthenticationToken(username, null, roles);
            }
        } catch (Exception e) {
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "YOU DON'T HAVE PERMISSIONS TO ACCESS THIS RESOURCE");
            } catch (Exception ee) {
                throw new AppException(InfoCodes.SECURITY, "getAuthentication - error sending UNAUTHORIZED response");
            }
        }
        return null;
    }
}