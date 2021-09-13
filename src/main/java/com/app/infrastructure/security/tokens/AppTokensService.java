package com.app.infrastructure.security.tokens;

import com.app.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

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
}