package finterest.lib;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_VALIDATION_REGEX = "^(.+)@(.+)$";

    @Override
    public boolean isValid(String field, ConstraintValidatorContext context) {
        if (field == null || field.trim().isEmpty()) {
            // Якщо поле порожнє, то вважаємо його коректним
            return true;
        } else {
            // В іншому випадку перевіряємо формат email
            return field.matches(EMAIL_VALIDATION_REGEX);
        }
    }
}
