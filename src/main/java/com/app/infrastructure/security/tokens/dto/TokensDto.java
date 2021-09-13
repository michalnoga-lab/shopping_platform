package com.app.infrastructure.security.tokens.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Tokens class
 * Represents a pair of tokens as an object
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TokensDto {

    private String accessToken;
    private String refreshToken;
}