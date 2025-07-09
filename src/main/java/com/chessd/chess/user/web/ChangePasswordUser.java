package com.chessd.chess.user.web;

import com.chessd.chess.custom_annotation.NoSpecialChars;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordUser {

    @NotNull(message = "Wymagane!")
    @NoSpecialChars(message = "Obecne hasło nie może zawierac znaków specjalnych!")
    @Size(min = 1, message = "Obecne hasło minimum długości jeden!")
    private String currentPassword;

    @NotNull(message = "Wymagane!")
    @NoSpecialChars(message = "Nowe hasło nie może zawierac znaków specjalnych!")
    @Size(min = 1, message = "Nowe hasło minimum długości jeden!")
    private String newPassword;

    @NotNull(message = "Wymagane!")
    @NoSpecialChars(message = "Powtórzenie nowego hasła nie może zawierac znaków specjalnych!")
    @Size(min = 1, message = "Powtórzenie nowego hasła minimum długości jeden!")
    private String newPasswordRepeat;
}
