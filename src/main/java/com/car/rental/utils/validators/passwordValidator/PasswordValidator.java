package com.car.rental.utils.validators.passwordValidator;

import com.car.rental.user.dto.UserAddDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, UserAddDto> {

    @Override
    public boolean isValid(UserAddDto userAddDto, final ConstraintValidatorContext context) {
        if(!userAddDto.getPassword().isBlank()){
            if(userAddDto.getPassword().equals(userAddDto.getRepeatPassword())){
                System.out.println();
                return true;
            }
        }
        return false;
    }
}
