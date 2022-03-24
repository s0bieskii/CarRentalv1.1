package com.car.rental.utils.validators.startAndEndDateValid;

import com.car.rental.car.dto.CarSearchDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartAndEndDateIsPresentValidator
        implements ConstraintValidator<StartAndEndDateIsPresentValid, CarSearchDto> {
    @Override
    public boolean isValid(CarSearchDto value, ConstraintValidatorContext context) {
        if (value.getEnd() == null && value.getStart() == null ||
                value.getEnd() != null && value.getStart() != null) {
            return true;
        }
        return false;
    }
}
