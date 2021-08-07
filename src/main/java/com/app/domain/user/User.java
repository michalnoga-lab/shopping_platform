package com.app.domain.user;

import lombok.*;

import java.time.LocalDateTime;

/**
 * Create User class
 * This class represents user purchasing auction products
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class User {

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