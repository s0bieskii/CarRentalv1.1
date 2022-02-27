package com.car.rental.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReturnReportAddDto {

    private String note;
    private boolean damaged;
    private Long carId;
    private Long employeeId;
}
