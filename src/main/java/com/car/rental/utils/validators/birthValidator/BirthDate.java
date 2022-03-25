package com.car.rental.utils.validators.birthValidator;


import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = BirthDateValidator.class)
@Target({TYPE, FIELD})
@Retention(RUNTIME)
@Documented
public @interface BirthDate {
    String message() default "You are under 18 years!";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};

}