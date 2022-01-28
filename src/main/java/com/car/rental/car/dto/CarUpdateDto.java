package com.car.rental.car.dto;

import com.car.rental.details.dto.CarDetailsUpdateDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CarUpdateDto {

    private int id;
    private String brand;
    private String model;
    private Boolean available;
    private Boolean deleted;
    private CarDetailsUpdateDto carDetails;
}
