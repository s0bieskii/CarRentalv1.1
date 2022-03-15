package com.car.rental.rent.user.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserSearchDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birth;
    private String email;
    private Boolean activated;
    private Boolean deleted;
}
