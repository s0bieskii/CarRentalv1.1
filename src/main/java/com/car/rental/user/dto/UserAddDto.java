package com.car.rental.user.dto;

import com.car.rental.utils.validators.birthValidator.BirthDate;
import com.car.rental.utils.validators.emailValidator.EmailValid;
import com.car.rental.utils.validators.passwordValidator.PasswordValid;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
@NoArgsConstructor
@PasswordValid
public class UserAddDto {

    @Size(min = 2, max = 15, message = "Please enter your name!")
    private String firstName;
    @Size(min = 2, max = 15, message = "Please enter your last name!")
    private String lastName;
    @BirthDate(message = "You must be 18 years old!")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birth;
    @NotBlank(message = "Email cant be empty!")
    @Email(message = "Invalid email")
    @EmailValid
    private String email;
    @Size(min = 8, message = "Password is too short!")
    private String password;
    private String repeatPassword;
    private boolean termsAccepted;
}
