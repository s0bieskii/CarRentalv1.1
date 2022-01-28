package com.car.rental.details.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CarDetailsUpdateDto {

    private int id;
    private String vin;
    private String color;
    private Integer registrationYear;
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
