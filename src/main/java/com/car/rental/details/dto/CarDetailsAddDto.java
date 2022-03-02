package com.car.rental.details.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CarDetailsAddDto {

    private String vin;
    private String color;
    private Integer registrationYear;
    private BigDecimal price;
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
