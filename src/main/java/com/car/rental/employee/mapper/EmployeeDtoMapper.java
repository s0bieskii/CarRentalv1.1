package com.car.rental.employee.mapper;

import com.car.rental.employee.Employee;
import com.car.rental.employee.dto.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeDtoMapper {
    EmployeeDtoMapper CAR_DTO_MAPPER = Mappers.getMapper(EmployeeDtoMapper.class);

    EmployeeDto employeeToEmployeeDto(Employee employee);

}
