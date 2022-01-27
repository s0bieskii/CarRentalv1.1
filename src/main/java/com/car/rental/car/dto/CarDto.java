package com.car.rental.car.dto;

import com.car.rental.details.dto.CarDetailsDto;
import com.car.rental.rental.dto.RentalWithoutEmployeeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CarDto {

    private int id;
    private String brand;
    private String model;
    private boolean available;
    private boolean deleted;
    private CarDetailsDto carDetails;
    private RentalWithoutEmployeeDto rental;
}
