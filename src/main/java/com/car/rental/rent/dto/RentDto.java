package com.car.rental.rent.dto;

import com.car.rental.car.dto.CarDto;
import com.car.rental.user.dto.UserDto;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RentDto {

    //TODO Add Rental from which was rented
    private Long id;
    private CarDto car;
    private UserDto user;
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime startDate;
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime endDate;
    private Double finalPrice;
    private boolean confirmed;
    private boolean returned;

    public String starDateGetString(){
        return startDate.toString().replaceAll("T", " ");
    }

    public String endDateGetString(){
        return endDate.toString().replaceAll("T", " ");
    }
}
