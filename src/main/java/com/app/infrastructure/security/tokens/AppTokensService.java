package com.app.infrastructure.security.tokens;

import com.app.domain.user.repository.UserRepository;
import com.app.infrastructure.security.dto.RefreshTokenDto;
import com.app.infrastructure.security.tokens.dto.TokensDto;
import com.app.infrastructure.security.tokens.exception.AppTokensServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

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
    private String tokensPrefix;

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

    public UsernamePasswordAuthenticationToken parseAccessToken(String header) {
        if (header == null) {
            throw new AppTokensServiceException("token header is null");
        }

        if (!header.startsWith(tokensPrefix)) {
            throw new AppTokensServiceException("Token has invalid format");
        }

        var token = header.replace(tokensPrefix, "");
        var userId = id(token);

        return userRepository
                .findById(userId)
                .map(user -> {
                    var userCredentials = user.toGetAuthorization();

                    return new UsernamePasswordAuthenticationToken(
                            userCredentials.getEmail(),
                            null,
                            List.of(new SimpleGrantedAuthority(userCredentials.getRole().toString())));
                }).orElseThrow(() -> new AppTokensServiceException("Authorization failed"));
    }

    public TokensDto refreshTokens(RefreshTokenDto refreshTokenDto) {
        if (refreshTokenDto == null) {
            throw new AppTokensServiceException("Refresh token object is null");
        }

        var refreshToken = refreshTokenDto.getToken();

        if (refreshToken == null) {
            throw new AppTokensServiceException("Token is null");
        }

        if (isTokenNotValid(refreshToken)) {
            throw new AppTokensServiceException("Refresh token has been expired");
        }

        var accessTokenExpirationTimeMsFromToken = accessTokenExpirationTimeMs(refreshToken);

        if (accessTokenExpirationTimeMsFromToken < System.currentTimeMillis()) {
            throw new AppTokensServiceException("Can't refresh access token - refresh token has been expired");
        }

        var userId = id(refreshToken);
        var currentDate = new Date();
        var accessTokenExpirationDate = new Date(currentDate.getTime() + accessTokenExpirationTimeMs);
        var refreshTokenExpirationDate = expirationDate(refreshToken);

        var accessToken = Jwts
                .builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(accessTokenExpirationDate)
                .setIssuedAt(currentDate)
                .signWith(secretKey)
                .compact();

        var newRefreshToken = Jwts
                .builder()
                .setSubject(String.valueOf(userId))
                .setExpiration(refreshTokenExpirationDate)
                .setIssuedAt(currentDate)
                .claim(refreshTokenProperty, accessTokenExpirationDate.getTime())
                .compact();

        return TokensDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private Claims claims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    private Long id(String token) {
        return Long.parseLong(claims(token).getSubject());
    }

    private Date expirationDate(String token) {
        return claims(token).getExpiration();
    }

    private boolean isTokenNotValid(String token) {
        return expirationDate(token).before(new Date());
    }

    private Long accessTokenExpirationTimeMs(String token) {
        return claims(token).get(refreshTokenProperty, Long.class);
    }
}