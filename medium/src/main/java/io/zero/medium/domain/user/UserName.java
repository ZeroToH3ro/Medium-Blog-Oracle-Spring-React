package io.zero.medium.domain.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class UserName {
    @Column(name = "name", nullable = false)
    private String name;

    public UserName(String name) {this.name = name;}

    protected UserName(){}

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        final var userName = (UserName) obj;
        return name.equals(userName.name);
    }
}
