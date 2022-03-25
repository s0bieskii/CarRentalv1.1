package com.car.rental.utils.validators.startAndEndDateValidator;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = StartAndEndDateIsPresentValidator.class)
@Target({ TYPE})
@Retention(RUNTIME)
@Documented
public @interface StartAndEndDateIsPresentValid {
    String message() default "Start and end date must be given!";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
