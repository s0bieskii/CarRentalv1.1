package com.car.rental.details.dto;

import com.car.rental.utils.Config;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CarDetailsUpdateDto {

    private Long id;
    private String vin;
    private String color;
    private Integer registrationYear;
    private Double price;
    private String segment;
    private Integer doors;
    private Integer seats;
    private String registrationNumber;
    private Integer mileage;
    @JsonFormat(pattern = Config.GLOBAL_LOCAL_DATA_TIME_FORMAT)
    private LocalDate inspection;
    @JsonFormat(pattern = Config.GLOBAL_LOCAL_DATA_TIME_FORMAT)
    private LocalDate insurance;
    private String fuel;
    private Double averageConsumption;
    private String transmission;
}
