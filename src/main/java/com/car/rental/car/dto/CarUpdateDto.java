package com.car.rental.car.dto;

import com.car.rental.details.dto.CarDetailsUpdateDto;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Data
public class CarUpdateDto {

    private String brand;
    private String model;
    private boolean available;
    private boolean deleted;
    private CarDetailsUpdateDto carDetails;

    public CarUpdateDto(CarDetailsUpdateDto carDetails) {
        this.carDetails = carDetails;
    }
}
