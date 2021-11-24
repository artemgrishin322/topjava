package ru.javawebinar.topjava.repository.jdbc;

import ru.javawebinar.topjava.model.AbstractBaseEntity;

import javax.validation.*;
import java.util.Set;

public class ValidationHandler {
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    protected static <T extends AbstractBaseEntity> void validate(T parameter) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(parameter);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
