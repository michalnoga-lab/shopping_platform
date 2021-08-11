package com.app.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * GetUserDto class
 * Returns User as DTO
 *
 * @see com.app.domain.user.User
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class GetUserDto {

    /**
     * User ID
     */
    Long id;

    /**
     * User first name
     */
    String name;

    /**
     * User last name
     */
    String surname;

    /**
     * User registration email
     * This email is used to log in to application
     */
    String email;

    /**
     * Current user password
     */
    String password;

    /**
     * Date and time of account creation
     */
    LocalDateTime createdAt;

    /**
     * IP address used to crate account
     */
    String accountCreationIp;

    /**
     * IP address used for last log into application
     */
    String lastLoginIp;
}