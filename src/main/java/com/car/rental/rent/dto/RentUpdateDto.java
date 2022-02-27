package com.car.rental.rent.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RentUpdateDto {

    private Long id;
    private String comment;
    private BigDecimal finalPrice;
    private boolean confirmed;
    private boolean returned;
    private boolean deleted;
}
