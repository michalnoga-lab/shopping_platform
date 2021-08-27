package com.app.domain.verification_token;

import com.app.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;

import static com.app.domain.user.UserUtil.toId;

/**
 * VerificationToken class
 * Represents User's verification token
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class VerificationToken {

    /**
     * Token ID
     */
    Long id;

    /**
     * Token string value
     */
    String token;

    /**
     * Token expiration time
     */
    LocalDateTime expirationTime;

    /**
     * User owning token
     *
     * @see User
     */
    User user;

    /**
     * Check if token is valid
     *
     * @return true if token is valid or false if token has expired
     */
    public boolean isValid() {
        return LocalDateTime.now().isBefore(expirationTime);
    }

    /**
     * Returns ID of User, which has token
     *
     * @return User ID
     * @see User
     */
    public Long getUserId() {
        return toId.apply(user);
    }
}