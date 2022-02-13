package com.car.rental.details.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarDetailsDto {

    int id;
    private String color;
    private Integer registrationYear;
    private BigDecimal price;
    private String segment;
    private Integer doors;
    private Integer seats;
    private String fuel;
    private double averageConsumption;
    private String transmission;
}
