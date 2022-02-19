package com.car.rental.employee.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmployeeSearchDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String employmentPosition;
    private Long rentalId;
}
