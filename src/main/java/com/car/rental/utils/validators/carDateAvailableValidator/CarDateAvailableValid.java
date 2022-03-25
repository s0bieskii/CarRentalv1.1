package com.car.rental.utils.validators.carDateAvailableValidator;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = CarDateAvailableValidator.class)
@Target({TYPE})
@Retention(RUNTIME)
@Documented
public @interface CarDateAvailableValid {
    String message() default "Car is not available in this date!";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
