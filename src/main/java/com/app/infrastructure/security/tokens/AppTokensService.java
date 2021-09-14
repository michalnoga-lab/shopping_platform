package com.app.infrastructure.security.tokens;

import com.app.domain.user.repository.UserRepository;
import com.app.infrastructure.security.tokens.dto.TokensDto;
import com.app.infrastructure.security.tokens.exception.AppTokensServiceException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.time.LocalDateTime;
import java.util.Date;

import static com.app.domain.user.UserUtil.toId;

/**
 * Tokens service class
 * For generating and refreshing tokens
 */
@Service
@RequiredArgsConstructor
public class AppTokensService {

    @Value("${tokens.access-token.expiration-time-ms}")
    private Long accessTokenExpirationTimeMs;

    @Value("${tokens.refresh-token.expiration-time-ms}")
    private Long refreshTokenExpirationTimeMs;

    @Value("${tokens.refresh-token.access-token-expiration-time-ms-property}")
    private String refreshTokenProperty;

    @Value("${tokens.prefix}")
    private String tokenPrefix;

    private final UserRepository userRepository;
    private final SecretKey secretKey;

    public TokensDto generateTokens(Authentication authentication) {

        var username = authentication.getName();
        var userId = userRepository
                .findByEmail(username)
                .map(toId)
                .orElseThrow(() -> new AppTokensServiceException("Cannot find user with email " + authentication.getName()));
        var currentDate = new Date();
        var accessTokenExpirationDate = new Date(currentDate.getTime() + accessTokenExpirationTimeMs);
        var refreshTokenExpirationDate = new Date(currentDate.getTime() + refreshTokenExpirationTimeMs);

        var accessToken = Jwts
                .builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(accessTokenExpirationDate)
                .setIssuedAt(currentDate)
                .signWith(secretKey)
                .compact();

        var refreshToken = Jwts
                .builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(refreshTokenExpirationDate)
                .setIssuedAt(currentDate)
                .claim(refreshTokenProperty, accessTokenExpirationDate.getTime())
                .signWith(secretKey)
                .compact();

        return TokensDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}