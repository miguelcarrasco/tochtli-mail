package com.tlacaelelsoftware.tochtlimail.common;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Hibernate Validator helper to validate classes
 */
public class ValidatorHelper<T> {
    private Validator validator;

    public ValidatorHelper() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    public void validate(T object) throws IllegalArgumentException{
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);

        if (constraintViolations.size() > 0) {
            ConstraintViolation<T> firstConstraintViolation = constraintViolations.iterator().next();
            String invalidMessage = firstConstraintViolation.getMessage();

            throw new IllegalArgumentException(invalidMessage);
        }
    }
}
