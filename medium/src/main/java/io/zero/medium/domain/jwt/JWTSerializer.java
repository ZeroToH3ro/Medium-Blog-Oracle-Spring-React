package io.zero.medium.domain.jwt;

import io.zero.medium.domain.user.User;

public interface JWTSerializer {
    String jwtFromUser(User user);
}
