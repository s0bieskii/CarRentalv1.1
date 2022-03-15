package com.car.rental.rent.dto;

import com.car.rental.car.dto.CarDto;
import com.car.rental.user.dto.UserDto;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RentDto {

    private Long id;
    private CarDto   car;
    private UserDto user;
    private LocalDateTime start;
    private LocalDateTime end;
    private Double finalPrice;
    private boolean confirmed;
    private boolean returned;
}
