package com.car.rental.car.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CarSearchDto {

    private Integer id;
    private String brand;
    private String model;
    private Boolean available;
    private Boolean deleted;
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
