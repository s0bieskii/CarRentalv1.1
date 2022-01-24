package com.car.rental.details.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Data
public class CarDetailsUpdateDto {

    private String vin;
    private String color;
    private Integer registrationYear;
    private String type;
    private Double price;
    private String segment;
    private Integer doors;
    private Integer seats;
    private String registrationNumber;
    private Integer mileage;
    private LocalDate inspection;
    private LocalDate insurance;
    private String fuel;
    private Double averageConsumption;
    private String transmission;
}
