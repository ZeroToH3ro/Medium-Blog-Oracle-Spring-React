package io.zero.medium.domain.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.springframework.security.crypto.password.PasswordEncoder;

@Embeddable
public class Password {
    @Column(name = "password", nullable = false)
    private String encodedPassword;

    private Password(String encode) {
        this.encodedPassword = encode;
    }

    protected Password() {

    }

    static Password of(String rawPassword, PasswordEncoder passwordEncoder){
        return new Password(passwordEncoder.encode(rawPassword));
    }

    boolean matchesPassword(String rawPassword, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


}
