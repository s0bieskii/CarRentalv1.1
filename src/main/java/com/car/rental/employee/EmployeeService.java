package com.car.rental.employee;

import com.car.rental.employee.dto.EmployeeAddDto;
import com.car.rental.employee.dto.EmployeeDto;
import com.car.rental.employee.dto.EmployeeSearchDto;
import com.car.rental.employee.dto.EmployeeUpdateDto;
import com.car.rental.employee.mapper.EmployeeMapper;
import com.car.rental.employee.repository.EmployeeRepository;
import com.car.rental.utils.PageWrapper;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    public static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        LOGGER.info(
                "Creating EmployeeService with(" + employeeRepository + ", " + employeeMapper);
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public Employee addEmployee(EmployeeAddDto dto) {
        LOGGER.info("addEmployee(" + dto + ")");
        Employee employeeToAdd = employeeMapper.employeeAddDtoToEmployee(dto);
        Employee savedEmployee = employeeRepository.save(employeeToAdd);
        return savedEmployee;
    }

    public Page<EmployeeDto> getAll(Pageable pageable) {
        LOGGER.info("getAll(" + pageable);
        Page<EmployeeDto> employeesDto = employeeRepository.findAll(pageable)
                .map(employeeMapper::employeeToEmployeeDto);
        LOGGER.info("All founded employees: " + employeesDto);
        return employeesDto;
    }

    public EmployeeDto findById(Long id) {
        LOGGER.info("findById(" + id + ")");
        EmployeeDto employee = employeeRepository.findById(id)
                .map(employeeMapper::employeeToEmployeeDto).orElse(null);
        return employee;
    }

    public Page<EmployeeDto> search(Pageable pageable, EmployeeSearchDto employeeSearchDto) {
        LOGGER.info("search(" + pageable + ", " + employeeSearchDto + ")");
        List<EmployeeDto> employeeDto = employeeRepository.find(employeeSearchDto).stream()
                .map(employeeMapper::employeeToEmployeeDto).collect(Collectors.toList());
        LOGGER.info("Found: " + employeeDto);
        return PageWrapper.listToPage(pageable, employeeDto);
    }

    public EmployeeDto updateEmployee(EmployeeUpdateDto employeeUpdateDto) {
        LOGGER.info("updateCar(" + employeeUpdateDto + ")");
        if (employeeUpdateDto.getId() != null && employeeRepository.existsById(employeeUpdateDto.getId())) {
            Employee employee = employeeMapper.employeeUpdateDtoToEmployee(employeeUpdateDto);
            employeeRepository.save(employee);
            LOGGER.info("Employee successfully updated");
            return employeeMapper.employeeToEmployeeDto(employee);
        }
        LOGGER.info("Employee update failed!");
        return null;
    }

    public boolean deleteEmployee(Long id) {
        LOGGER.info("deleteCar(" + id + ")");
        if (employeeRepository.existsById(id)) {
            LOGGER.info("Employee with given ID exists");
            Employee employee = employeeRepository.getById(id);
            employee.setDeleted(true);
            employeeRepository.save(employee);
            LOGGER.info("Successful deleted car with id=" + id);
            return true;
        }
        LOGGER.info("Employee with given id not exist");
        return false;
    }
}
