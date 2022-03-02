package com.car.rental.car.dto;

import com.car.rental.details.dto.CarDetailsUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarUpdateDto {

    private Long id;
    private String brand;
    private String model;
    private Boolean available;
    private Boolean deleted;
    private CarDetailsUpdateDto carDetails;

    public CarUpdateDto(CarDetailsUpdateDto carDetails) {
        this.carDetails = carDetails;
    }
}
