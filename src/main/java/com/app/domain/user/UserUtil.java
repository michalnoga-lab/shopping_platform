package com.app.domain.user;

import java.util.function.Function;

public interface UserUtil {
    Function<User, Long> toId = User::getId;
}