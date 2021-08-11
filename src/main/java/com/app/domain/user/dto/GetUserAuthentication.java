package com.app.domain.user.dto;

import com.app.domain.user.type.Role;
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
    Boolean enabled = false;

    /**
     * User role in application
     */
    Role role;
}