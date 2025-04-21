package com.chessd.chess.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class NoSpecialCharsValidator implements ConstraintValidator<NoSpecialChars, String> {
    private static final String DISALLOWED_CHARACTERS_REGEX = "[^a-zA-Z0-9ąćęłńóśżźĄĆĘŁŃÓŚŻŹ_ ]";

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        if(string == null || string.isEmpty()){
            return true;
        }
        return !Pattern.compile(DISALLOWED_CHARACTERS_REGEX).matcher(string).find();
    }
}
