package com.car.rental.car.dto;

import com.car.rental.details.dto.CarDetailsDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarDto {

    private Long id;
    private String brand;
    private String model;
    private boolean available;
    private boolean deleted;
    private CarDetailsDto carDetails;
}
