package com.chessd.chess.user.web;

import com.chessd.chess.customAnnotation.NoSpecialChars;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser {
    @NoSpecialChars(message = "Nazwa użytkownia zawiera niedozwolone znaki!")
    private String userName;

    @NoSpecialChars(message = "Imie zawiera niedozwolone znaki!")
    private String firstName;

    @NoSpecialChars(message = "Nazwisko użytkownia zawiera niedozwolone znaki!")
    private String lastName;

    @Email(message = "Niewlaściwy email")
    private String email;

    @NoSpecialChars(message = "Kraj zawiera niedozwolone znaki!")
    private String country;

    @NoSpecialChars(message = "Płeć zawiera niedozwolone znaki!")
    private String gender;

    public HashMap<String, Object> allNotNullFields() {
        HashMap<String, Object> result = new HashMap<>();
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.get(this) != null) {
                    result.put(field.getName(), field.get(this));
                }

            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }
        }
        return result;
    }
}
