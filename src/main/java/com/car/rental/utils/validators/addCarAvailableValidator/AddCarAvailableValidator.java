package com.car.rental.utils.validators.addCarAvailableValidator;

import com.car.rental.car.dto.CarAddDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AddCarAvailableValidator implements ConstraintValidator<AddCarAvailableValid, CarAddDto> {
    @Override
    public boolean isValid(CarAddDto carAddDto, ConstraintValidatorContext context) {

        if(carAddDto.getCarDetails().getRentalId()==null && carAddDto.isAvailable()) {
            return false;
        }
        return true;
    }
}
