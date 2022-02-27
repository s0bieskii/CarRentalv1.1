package com.car.rental.rental.dto;

import com.car.rental.employee.dto.EmployeeDto;
import java.util.List;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RentalDto {

    private Long id;
    private String country;
    private String city;
    private String postCode;
    private String street;
    private String phone;
    private boolean deleted;
    @OneToMany
    private List<EmployeeDto> employees;
}
