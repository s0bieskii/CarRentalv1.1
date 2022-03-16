package com.car.rental.utils.validators.emailValidator;

import com.car.rental.user.User;
import com.car.rental.user.repository.UserRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailValidator implements ConstraintValidator<EmailValid, String> {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        User user = userRepository.findUserByEmail(email);
        if(user!=null){
            return false;
        }
        return true;
    }
}
