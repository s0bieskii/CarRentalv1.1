package com.car.rental.details.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CarDetailsAddDto {

    private String vin;
    private String color;
    private Integer registrationYear;
    private String type;
    private Double price;
    private String segment;
    private Integer doors;
    private Integer seats;
    private String registrationNumber;
    private int mileage;
    private LocalDate inspection;
    private LocalDate insurance;
    private String fuel;
    private double averageConsumption;
    private String transmission;
}
