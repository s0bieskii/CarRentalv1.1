package com.car.rental.details;

import com.car.rental.utils.Config;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class CarDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vin;
    private String color;
    private Integer registrationYear;
    private BigDecimal price;
    private String segment;
    private Integer doors;
    private Integer seats;
    private String registrationNumber;
    private int mileage;
    @JsonFormat(pattern = Config.GLOBAL_LOCAL_DATA_FORMAT)
    private LocalDate inspection;
    @JsonFormat(pattern = Config.GLOBAL_LOCAL_DATA_FORMAT)
    private LocalDate insurance;
    private String fuel;
    private double averageConsumption;
    private String transmission;
}