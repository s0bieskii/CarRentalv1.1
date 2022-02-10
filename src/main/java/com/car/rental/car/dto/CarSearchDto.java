package com.car.rental.car.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end;
}
