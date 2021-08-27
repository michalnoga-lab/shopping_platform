package com.app.domain.verification_token;

import com.app.domain.user.User;
import lombok.*;

import java.time.LocalDateTime;

import static com.app.domain.user.UserUtil.toId;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode
@ToString
public class VerificationToken {

    Long id;
    String token;
    LocalDateTime dateTime;
    User user;

    public boolean isValid() {
        return LocalDateTime.now().isBefore(dateTime);
    }

    public Long getUserId() {
        return toId.apply(user);
    }
}