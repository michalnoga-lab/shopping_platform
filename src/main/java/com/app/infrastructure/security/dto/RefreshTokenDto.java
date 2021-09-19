package com.app.infrastructure.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Refresh token class
 * Represents refresh token as DTO
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RefreshTokenDto {

    /**
     * Token payload
     */
    private String token;
}