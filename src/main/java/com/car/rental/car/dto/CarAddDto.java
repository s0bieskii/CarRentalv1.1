package com.car.rental.car.dto;

import com.car.rental.details.dto.CarDetailsAddDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CarAddDto {

    private String brand;
    private String model;
    private boolean available;
    private boolean deleted;
    private CarDetailsAddDto carDetails;

    public CarAddDto(CarDetailsAddDto carDetails) {
        this.carDetails = carDetails;
    }
}
