package ru.javawebinar.topjava.repository.jdbc;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.Set;

public class ValidationHandler {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    protected void validate(Object parameter) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(parameter);

        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for (ConstraintViolation<Object> violation : constraintViolations) {
                String exceptionMessage = String.format("path: [%s], value: [%s], message: [%s]",
                        violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage());
                msg.append(exceptionMessage)
                        .append("\n");
            }
            throw new ValidationException(msg.toString());
        }
    }
}
