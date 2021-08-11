package com.app.domain.user.dto;

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
}