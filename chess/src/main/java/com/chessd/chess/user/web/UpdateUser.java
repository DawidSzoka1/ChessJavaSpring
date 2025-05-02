package com.chessd.chess.user.web;

import com.chessd.chess.customAnnotation.NoSpecialChars;
import com.chessd.chess.user.entity.User;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

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

    private MultipartFile profilePicture;

    @Email(message = "Niewlaściwy email")
    private String email;

    @NoSpecialChars(message = "Kraj zawiera niedozwolone znaki!")
    private String country;

    @NoSpecialChars(message = "Płeć zawiera niedozwolone znaki!")
    private String gender;

    public static UpdateUser fromUser(User user)  {
        UpdateUser updateUser = new UpdateUser();
        updateUser.setUserName(user.getUserName());
        updateUser.setFirstName(user.getFirstName());
        updateUser.setLastName(user.getLastName());
        updateUser.setEmail(user.getEmail());
        updateUser.setGender(user.getGender());
        updateUser.setCountry(user.getCountry());
        return updateUser;
    }

    public Map<String, Object> allNotNullFields() {
        HashMap<String, Object> result = new HashMap<>();
        for (Field field : this.getClass().getDeclaredFields()) {
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
