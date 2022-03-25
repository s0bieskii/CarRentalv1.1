package com.car.rental.car.dto;

import com.car.rental.details.dto.CarDetailsAddDto;
import com.car.rental.utils.validators.addCarAvailableValidator.AddCarAvailableValid;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AddCarAvailableValid
public class CarAddDto {

    @NotBlank(message = "Pleas enter brand")
    private String brand;
    @NotBlank(message = "Pleas enter model")
    private String model;
    private boolean available;
    private boolean deleted;
    @Valid
    private CarDetailsAddDto carDetails;

    public CarAddDto(CarDetailsAddDto carDetails) {
        this.carDetails = carDetails;
    }
}
