package com.car.rental.report.dto;

import com.car.rental.car.dto.CarDto;
import com.car.rental.employee.dto.EmployeeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReturnReportDto {

    private Long id;
    private String note;
    private Boolean damaged;
    private CarDto car;
    private EmployeeDto employee;
    private Boolean deleted;
}
