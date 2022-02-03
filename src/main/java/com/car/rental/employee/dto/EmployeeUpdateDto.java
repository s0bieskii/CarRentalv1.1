package com.car.rental.employee.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeUpdateDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String employmentPosition;
    private BigDecimal salaryPerHour;
    private Boolean deleted;

}
