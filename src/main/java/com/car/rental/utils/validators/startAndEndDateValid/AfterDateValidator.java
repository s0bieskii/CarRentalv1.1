package com.car.rental.utils.validators.startAndEndDateValid;

import com.car.rental.car.dto.CarSearchDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AfterDateValidator implements ConstraintValidator<AfterDateValid, CarSearchDto> {
    @Override
    public boolean isValid(CarSearchDto carSearchDto, final ConstraintValidatorContext context) {
        if (carSearchDto.getStart() == null || carSearchDto.getEnd() == null) {
            return true;
        }
        if(carSearchDto.getStart()!=null && carSearchDto.getEnd() != null){
           return carSearchDto.getEnd().isAfter(carSearchDto.getStart());
        }
        return false;
    }
}
