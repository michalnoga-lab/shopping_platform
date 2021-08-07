package com.app.domain.user;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class User {

    Long id;
    String name;
    String surname;
    String email;
    String password;
    LocalDateTime createdAt;
    String accountCreationIp;
    String lastLoginIp;
}