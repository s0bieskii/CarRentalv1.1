package com.car.rental.utils.validators.addCarAvailableValidator;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = AddCarAvailableValidator.class)
@Target({ TYPE})
@Retention(RUNTIME)
@Documented
public @interface AddCarAvailableValid {

    String message() default "Car cant be available with no assigned rental office!";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
