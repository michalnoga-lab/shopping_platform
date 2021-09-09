package com.app.domain.user;

import com.app.domain.user.dto.CreateUserResponseDto;
import com.app.domain.user.dto.GetUserAuthentication;
import com.app.domain.user.dto.GetUserAuthorization;
import com.app.domain.user.dto.GetUserDto;
import com.app.domain.user.type.Role;
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
     * IP address used for last login into application
     */
    String lastLoginIp;

    /**
     * User role in application
     */
    Role role;

    /**
     * Is user enabled to log in and use application
     */
    Boolean enabled;

    /**
     * Enables user to log in and use application
     */
    public void activate() {
        enabled = true;
    }

    /**
     * Converts User to CreateUserResponseDto
     *
     * @return CreateUserResponseDto
     */
    public CreateUserResponseDto toCreateUserResponseDto() {
        return CreateUserResponseDto
                .builder()
                .id(id)
                .build();
    }

    /**
     * Converts User to AuthenticationDto
     *
     * @return GetUserAuthentication
     */
    public GetUserAuthentication toGetUserAuthentication() {
        return GetUserAuthentication
                .builder()
                .email(email)
                .password(password)
                .enabled(enabled)
                .role(role)
                .build();
    }

    /**
     * Converts User to AuthorizationDto
     *
     * @return GetUserAuthorization
     */
    public GetUserAuthorization toGetAuthorization() {
        return GetUserAuthorization
                .builder()
                .email(email)
                .build();
    }

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
                .role(role)
                .enabled(enabled)
                .build();
    }
}