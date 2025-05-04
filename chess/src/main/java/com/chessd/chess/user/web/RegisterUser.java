package com.chessd.chess.user.web;

import com.chessd.chess.custom_annotation.NoSpecialChars;
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

    @NotNull(message = "Wymagane!")
    @Size(min=1, message = "Login minimum długości jeden!")
    @NoSpecialChars(message = "Nazwa użytkownia zawiera niedozwolone znaki!")
    private String userName;

    @NotNull(message = "Wymagane!")
    @Size(min=1, message = "Hasło minimum długości jeden!")
    @NoSpecialChars(message = "Hasło zawiera niedozwolone znaki!")
    private String password;

    @NotNull(message = "Wymagane!")
    @Size(min=1, message = "Powtórzenie hasła minimum długości jeden!")
    @NoSpecialChars(message = "Powtórzenie hasła zawiera niedozwolone znaki!")
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
