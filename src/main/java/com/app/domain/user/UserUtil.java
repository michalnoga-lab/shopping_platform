package com.app.domain.user;

import java.util.function.Function;

/**
 * UserUtil interface
 * Provides utilities for User
 *
 * @see User
 */
public interface UserUtil {
    Function<User, Long> toId = User::getId;
}