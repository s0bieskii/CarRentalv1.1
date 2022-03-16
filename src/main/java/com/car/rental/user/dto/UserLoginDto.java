package com.car.rental.user.dto;

import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserLoginDto {

    @Email
    private String email;
    private String password;
}
