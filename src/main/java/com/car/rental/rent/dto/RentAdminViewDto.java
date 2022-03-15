package com.car.rental.rent.dto;

import com.car.rental.car.dto.CarDto;
import com.car.rental.report.dto.ReturnReportDto;
import com.car.rental.rent.user.dto.UserDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RentAdminViewDto {

    private Long id;
    private CarDto car;
    private UserDto user;
    private ReturnReportDto report;
    private LocalDateTime start;
    private LocalDateTime end;
    private String comment;
    private BigDecimal finalPrice;
    private boolean confirmed;
    private boolean returned;
    private boolean deleted;
}
