package com.car.rental.rental.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RentalWithoutEmployeeDto {

    private int id;
    private String country;
    private String city;
    private String postCode;
    private String street;
    private String phone;
    private boolean deleted;
}
