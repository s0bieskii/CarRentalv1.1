package com.car.rental.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReturnReportSearchDto {

    private Long id;
    private Boolean damaged;
    private Long carId;
    private Long employeeId;
    private Boolean deleted;
}
