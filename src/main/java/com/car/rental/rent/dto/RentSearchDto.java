package com.car.rental.rent.dto;

import com.car.rental.utils.Config;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class RentSearchDto {

    private Long id;
    private Long carId;
    private Long userId;
    private Long employeeId;
    @JsonFormat(pattern = Config.GLOBAL_LOCAL_DATA_TIME_FORMAT)
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime start;
    @JsonFormat(pattern = Config.GLOBAL_LOCAL_DATA_TIME_FORMAT)
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime end;
    private Boolean confirmed;
    private Boolean returned;
    private Boolean deleted;
    private Boolean damaged;
}
