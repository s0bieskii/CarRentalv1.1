package com.car.rental.rental.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentalUpdateDto {

    private Long id;
    private String country;
    private String city;
    private String postCode;
    private String street;
    private String phone;
    private Boolean deleted;

}
