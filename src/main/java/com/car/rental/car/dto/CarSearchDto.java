package com.car.rental.car.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarSearchDto {

    private Long id;
    private String brand;
    private String model;
    private Integer rental;
    private String color;
    private Integer registrationYear;
    private Double price;
    private String segment;
    private Integer doors;
    private Integer seats;
    private String fuel;
    private String transmission;
    private LocalDateTime start;
    private LocalDateTime end;
}
