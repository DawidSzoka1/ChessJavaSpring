package com.chessd.chess.web;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebUser {

    @NotNull(message = "is required")
    @Size(min=1, message = "user name min 1 length")
    private String userName;

    @NotNull(message = "is required")
    @Size(min=1, message = "password min 1 length")
    private String password;

    @NotNull(message = "is required")
    @Size(min=1, message = "password2 min 1 length")
    private String password2;

    @Size(max = 20, message = "first name cant by longer than 20")
    private String firstName;

    @Size(max = 20, message = "last name cant by longer than 20")
    private String lastName;

    private String email;

    @Size(max = 1)
    private String gender;

    private String country;
}
