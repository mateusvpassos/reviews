package br.com.mateus.crud.endpoint.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.mateus.crud.endpoint.annotation.AtLeastZero;

public class AtLeastZeroValidator implements ConstraintValidator<AtLeastZero, Number> {

    @Override
    public boolean isValid(final Number value, final ConstraintValidatorContext context) {
        return value.doubleValue() >= 0;
    }

}
