package com.car.rental.rent.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RentSearchDto {

    private Long id;
    private Long carId;
    private Long userId;
    private Long employeeId;
    private LocalDateTime start;
    private LocalDateTime end;
    private Boolean confirmed;
    private Boolean returned;
    private Boolean deleted;
    private Boolean damaged;
}
