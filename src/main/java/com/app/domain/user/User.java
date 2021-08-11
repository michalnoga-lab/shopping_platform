package com.app.domain.user;

import com.app.domain.user.dto.GetUserDto;
import lombok.*;

import java.time.LocalDateTime;

/**
 * User class
 * Represents user purchasing auction products
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

    /**
     * Converts User to UserDto
     *
     * @return GetUserDto
     */
    public GetUserDto toGetUserDto() {
        return GetUserDto
                .builder()
                .id(id)
                .name(name)
                .surname(surname)
                .email(email)
                .password(password)
                .createdAt(createdAt)
                .accountCreationIp(accountCreationIp)
                .lastLoginIp(lastLoginIp)
                .build();
    }
}