package com.car.rental.employee.mapper;

import com.car.rental.employee.Employee;
import com.car.rental.employee.dto.EmployeeAddDto;
import com.car.rental.employee.dto.EmployeeDto;
import com.car.rental.employee.dto.EmployeeUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper CAR_DTO_MAPPER = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto employeeToEmployeeDto(Employee employee);

    Employee employeeAddDtoToEmployee(EmployeeAddDto employeeAddDto);

    Employee employeeUpdateDtoToEmployee(EmployeeUpdateDto employeeUpdateDto);
}
