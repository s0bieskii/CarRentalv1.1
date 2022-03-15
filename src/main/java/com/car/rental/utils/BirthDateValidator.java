package com.car.rental.utils;

import java.time.LocalDate;
import java.time.Period;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {
    @Override
    public boolean isValid(final LocalDate valueToValidate, final ConstraintValidatorContext context) {
        LocalDate dateInCalender = LocalDate.now();
        LocalDate valid = valueToValidate;
        if(valid==null || valid.isAfter(dateInCalender) || valid.isEqual(dateInCalender)){
            return false;
        }
        return Period.between(valid, dateInCalender).getYears() >= 18;
    }
}