package br.com.mateus.crud.endpoint.validator;

import javax.validation.ConstraintValidator;

import br.com.mateus.crud.endpoint.annotation.AtLeastZero;

public class AtLeastZeroValidator implements ConstraintValidator<AtLeastZero, Number> {

    @Override
    public boolean isValid(Number value, javax.validation.ConstraintValidatorContext context) {
        return value.doubleValue() >= 0;
    }

}
