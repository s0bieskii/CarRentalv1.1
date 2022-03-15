package com.car.rental.user.dto;

import com.car.rental.utils.BirthDate;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class UserAddDto {

    @NotBlank(message = "First name can't be empty!")
    private String firstName;
    @NotBlank(message = "Last name can't be empty!")
    private String lastName;
    @NotNull(message = "Birth can't be empty!")
    @BirthDate(message = "You must be 18 years old!")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birth;
    @NotBlank
    @Email(message = "Invalid email")
    private String email;
    @Size(min = 8, message = "Password must contain min 8 characters!")
    private String password;
}
