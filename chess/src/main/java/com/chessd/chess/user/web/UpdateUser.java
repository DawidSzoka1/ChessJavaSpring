package com.chessd.chess.user.web;

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

    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String country;
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
