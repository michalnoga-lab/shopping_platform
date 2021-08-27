package com.app.domain.verification_token;

import com.app.domain.user.User;
import com.app.domain.user.type.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
public class VerificationTokenTests {

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Check if User token is valid")
    void isValid() {

        VerificationToken notExpiredToken = VerificationToken
                .builder()
                .id(1L)
                .token("token value")
                .expirationTime(LocalDateTime.now().plusMinutes(1))
                .user(User.builder().build())
                .build();

        VerificationToken expiredToken = VerificationToken
                .builder()
                .id(1L)
                .token("token value")
                .expirationTime(LocalDateTime.now().minusMinutes(1))
                .user(User.builder().build())
                .build();

        assertTrue(notExpiredToken.isValid());
        assertFalse(expiredToken.isValid());
    }

    @Test
    @DisplayName("Get User ID from token")
    void getUserId() {

        User user = User.builder()
                .id(1L)
                .name("name")
                .surname("surname")
                .email("email")
                .password("password")
                .createdAt(LocalDateTime.of(2021, 8, 24, 11, 38))
                .accountCreationIp("0.0.0.0")
                .lastLoginIp("0.0.0.0")
                .role(Role.ROLE_USER)
                .enabled(false)
                .build();

        VerificationToken verificationToken = VerificationToken
                .builder()
                .id(1L)
                .token("token value")
                .expirationTime(LocalDateTime.now().plusMinutes(1))
                .user(user)
                .build();

        Long expectedUserId = 1L;

        Long userIdFromToken = verificationToken.getUserId();

        assertThat(userIdFromToken).isEqualTo(expectedUserId);
    }
}