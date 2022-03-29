package com.car.rental.user.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserUpdateDto {

    private Long id;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private String email;
    private String password;
    private String passwordRepeat;
    private Boolean deleted;
}
