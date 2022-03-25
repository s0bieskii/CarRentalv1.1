package com.car.rental.utils.validators.startAndEndDateValidator;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = AfterDateValidator.class)
@Target({ TYPE})
@Retention(RUNTIME)
@Documented
public @interface AfterDateValid {
    String message() default "Start date is after end date!";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
