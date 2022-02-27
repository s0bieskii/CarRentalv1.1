package com.car.rental.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReturnReportUpdateDto {

    private Long id;
    private String note;
    private Boolean damaged;
    private Long carId;
    private Long employeeId;
}
