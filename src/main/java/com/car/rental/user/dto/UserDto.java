package com.car.rental.user.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birth;
    private boolean activated;
    private boolean deleted;
}
