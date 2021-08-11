package com.app.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GetUserAuthentication class
 * Returns user for authentication
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GetUserAuthentication {

    /**
     * User email for authentication
     */
    private String email;

    /**
     * User password for authentication
     */
    private String password;

    /**
     * Is user enabled /default true/
     */
    boolean enabled = true;
}