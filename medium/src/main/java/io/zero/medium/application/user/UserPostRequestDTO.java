package io.zero.medium.application.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.zero.medium.domain.user.Email;
import io.zero.medium.domain.user.UserName;
import io.zero.medium.domain.user.UserSignUpRequest;
import lombok.Value;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Value
public class UserPostRequestDTO {
    @javax.validation.constraints.Email
    String email;
    @NotBlank
    String username;
    @NotBlank
    String password;

    UserSignUpRequest toSignUpRequest() {
        return new UserSignUpRequest(
                new Email(email),
                new UserName(username),
                password);
    }
}
