package com.car.rental.details.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CarDetailsAddDto {

    @NotBlank(message = "Pleas enter VIN")
    @Pattern(regexp = "^([A-Z0-9]){17}$", message = "VIN always have 17 characters (uppercase letters and numbers)")
    private String vin;
    @NotBlank(message = "Enter color")
    @Length(min = 2, max = 12, message = "Enter correct color")
    private String color;
    @Min(value = 1990, message = "We don't accept such old cars")
    @NotNull(message = "Enter year")
    private Integer registrationYear;
    @NotNull(message = "Enter price")
    @Min(value = 20, message = "Minimal price is 20.00")
    private BigDecimal price;
    @NotBlank(message = "Pleas choose segment!")
    private String segment;
    @NotNull(message = "Enter doors")
    @Min(value = 2, message = "Car must have minimum 2 doors")
    private Integer doors;
    @NotNull(message = "Enter seats")
    @Min(value = 2, message = "Car must have minimum 2 seats")
    private Integer seats;
    private String registrationNumber;
    @NotNull(message = "Enter mileage")
    @Min(value = 0, message = "Mileage can't be negative")
    private Integer mileage;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inspection;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate insurance;
    @NotBlank(message = "Pleas choose fuel")
    private String fuel;
    @NotNull(message = "Enter average consumption")
    @Min(value = 0, message = "Average consumption can't be negative")
    private Double averageConsumption;
    @NotBlank(message = "Choose transmission")
    private String transmission;
    private Long rentalId;
}
