package com.chessd.chess.user.web;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {

    @NotNull(message = "is required")
    @Size(min=1, message = "user name min 1 length")
    private String userName;

    @NotNull(message = "is required")
    @Size(min=1, message = "password min 1 length")
    private String password;

    @NotNull(message = "is required")
    @Size(min=1, message = "password2 min 1 length")
    private String password2;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RegisterUser registerUser = (RegisterUser) o;
        return Objects.equals(userName, registerUser.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userName);
    }
}
