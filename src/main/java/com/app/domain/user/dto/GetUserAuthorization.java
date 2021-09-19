package com.app.domain.user.dto;

import com.app.domain.user.type.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * GetUserAuthorization class
 * Returns user for authorization
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GetUserAuthorization {

    /**
     * User email for authorization
     */
    private String email;

    /**
     * User role in application
     */
    private Role role;
}