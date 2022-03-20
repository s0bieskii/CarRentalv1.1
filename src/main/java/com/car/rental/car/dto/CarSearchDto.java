package com.car.rental.car.dto;

import com.car.rental.utils.Config;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CarSearchDto {

    private Long id;
    private String brand;
    private String model;
    private Long rental;
    private String color;
    private Integer registrationYear;
    private BigDecimal price;
    private String segment;
    private Integer doors;
    private Integer seats;
    private String fuel;
    private String transmission;
    @JsonFormat(pattern = Config.globalLocalDataTimeFormat)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start;
    @JsonFormat(pattern = Config.globalLocalDataTimeFormat)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end;
}
