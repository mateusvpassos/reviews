package br.com.mateus.crud.endpoint.validator;

import javax.validation.ConstraintValidator;

import br.com.mateus.crud.endpoint.annotation.GreaterThanZero;

public class GreaterThanZeroValidator implements ConstraintValidator<GreaterThanZero, Number> {

    @Override
    public boolean isValid(Number value, javax.validation.ConstraintValidatorContext context) {
        return value.doubleValue() > 0;
    }

}
