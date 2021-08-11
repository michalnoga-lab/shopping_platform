package com.app.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * CreateUserDto class
 * Returns created user
 *
 * @see com.app.domain.user.User
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CreateUserDto {

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
     * Current user password confirmation
     */
    String passwordConfirmation;

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
     * Same as accountCreationIP on user creation
     */
    String lastLoginIp;
}