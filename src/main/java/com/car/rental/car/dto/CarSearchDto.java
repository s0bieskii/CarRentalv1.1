package com.car.rental.car.dto;

import com.car.rental.utils.Config;
import com.car.rental.utils.validators.carDateAvailableValid.CarDateAvailableValid;
import com.car.rental.utils.validators.startAndEndDateValid.AfterDateValid;
import com.car.rental.utils.validators.startAndEndDateValid.StartAndEndDateIsPresentValid;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
@NoArgsConstructor
@StartAndEndDateIsPresentValid
@AfterDateValid
@CarDateAvailableValid
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
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime start;
    @JsonFormat(pattern = Config.globalLocalDataTimeFormat)
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime end;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public String getStartLocalDateTimeAsString(){
        LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatterDate = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return now.truncatedTo(ChronoUnit.MINUTES).format(formatterDate).toString();
    }

    public String getEndLocalDateTimeAsString(){
        if(start!=null){
            DateTimeFormatter formatterDate = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return start.plusDays(1).truncatedTo(ChronoUnit.MINUTES).format(formatterDate).toString();
        }
        return "";
    }

}
