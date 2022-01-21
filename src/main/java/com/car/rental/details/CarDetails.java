package com.car.rental.details;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class CarDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    int id;
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
