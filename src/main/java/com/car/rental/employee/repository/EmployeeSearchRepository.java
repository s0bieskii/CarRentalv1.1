package com.car.rental.employee.repository;


import com.car.rental.employee.Employee;
import com.car.rental.employee.dto.EmployeeSearchDto;
import java.util.List;

public interface EmployeeSearchRepository {

    List<Employee> find(EmployeeSearchDto employeeSearchDto);
}
