package com.car.rental.rental.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RentalAddDto {

    private String country;
    private String city;
    private String postCode;
    private String street;
    private String phone;
}
