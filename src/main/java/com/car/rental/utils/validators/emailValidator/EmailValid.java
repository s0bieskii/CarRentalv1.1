package com.car.rental.utils.validators.emailValidator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = EmailValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
@Documented
public @interface EmailValid {
    String message() default "This email is already in use!";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}
