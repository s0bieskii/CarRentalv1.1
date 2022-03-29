package com.car.rental.rent.dto;

import com.car.rental.utils.Config;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RentAddDto {

    private Long carId;
    private Long userId;
    @JsonFormat(pattern = Config.GLOBAL_LOCAL_DATA_TIME_FORMAT)
    private LocalDateTime startDate;
    @JsonFormat(pattern = Config.GLOBAL_LOCAL_DATA_TIME_FORMAT)
    private LocalDateTime endDate;
    private String comment;
    private boolean confirmed;
}
