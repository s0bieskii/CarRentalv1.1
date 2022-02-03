package com.car.rental.employee.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Component
public class EmployeeSearchDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String employmentPosition;
}
