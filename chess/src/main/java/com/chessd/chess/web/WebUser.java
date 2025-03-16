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

}
