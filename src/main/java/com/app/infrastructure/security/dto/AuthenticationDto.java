package com.app.infrastructure.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User Authentication class
 * Provides data for User authentication as DTO
 *
 * @see com.app.domain.user.User
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AuthenticationDto {

    /**
     * User email used for authentication
     */
    private String email;

    /**
     * User password used for authentication
     */
    private String password;
}