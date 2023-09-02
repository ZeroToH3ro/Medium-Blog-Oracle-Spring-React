package io.zero.medium.application.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.zero.medium.domain.user.User;
import lombok.Value;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static java.lang.String.valueOf;

@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Value
public class UserModel {
    String email;
    String username;
    String token;
    String bio;
    String image;

    static UserModel fromUserAndToken(User user, String token) {
        return new UserModel(
                valueOf(user.getEmail()),
                valueOf(user.getName()),
                token,
                "",
                "");
    }
}
