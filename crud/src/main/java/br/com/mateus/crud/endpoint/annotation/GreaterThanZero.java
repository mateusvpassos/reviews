package br.com.mateus.crud.endpoint.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import br.com.mateus.crud.endpoint.validator.GreaterThanZeroValidator;

@Documented
@Constraint(validatedBy = GreaterThanZeroValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface GreaterThanZero {

    String message() default "Should be greater than zero";

    Class<?>[] groups() default {};

    Class<? extends Payload> payload() default Payload.class;
}
