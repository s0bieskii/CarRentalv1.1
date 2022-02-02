package com.car.rental.employee;

import com.car.rental.employee.dto.EmployeeAddDto;
import com.car.rental.employee.dto.EmployeeDto;
import com.car.rental.employee.dto.EmployeeSearchDto;
import com.car.rental.employee.dto.EmployeeUpdateDto;
import com.car.rental.employee.mapper.EmployeeMapper;
import com.car.rental.employee.repository.EmployeeRepository;
import com.car.rental.utils.EntityUpdater;
import java.util.logging.Logger;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    public static final Logger LOGGER = Logger.getLogger(EmployeeService.class.getName());
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final EntityUpdater entityUpdater;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper,
                           EntityUpdater entityUpdater) {
        LOGGER.info(
                "Creating EmployeeService with(" + employeeRepository + ", " + employeeMapper + ", " + entityUpdater);
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.entityUpdater = entityUpdater;
    }

    public Employee addEmployee(EmployeeAddDto dto) {
        LOGGER.info("addEmployee(" + dto + ")");
        Employee employeeToAdd = employeeMapper.employeeAddDtoToEmployee(dto);
        LOGGER.info("Employee to save after mapping " + employeeToAdd);
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

    //TODO implement this
    public EmployeeDto search(Pageable pageable, EmployeeSearchDto employeeSearchDto) {
        return null;
    }

    //TODO refactor
    public EmployeeDto updateEmployeeByReflection(EmployeeUpdateDto employeeUpdate) {
        LOGGER.info("updateEmployeeByReflection(" + employeeUpdate + ")");
        if (!employeeRepository.existsById(employeeUpdate.getId())) {
            LOGGER.info("Employee with given ID not exist" + employeeUpdate.getId());
            return null;
        }
        LOGGER.info("Employee with given ID exist" + employeeUpdate.getId());
        Employee employeeToUpdate = (Employee) Hibernate.unproxy(employeeRepository.getById(employeeUpdate.getId()));
        Employee employeeAfterUpdate=employeeToUpdate;
        try {
            employeeAfterUpdate = (Employee) entityUpdater.updateEntity(employeeToUpdate, employeeUpdate);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        employeeRepository.save(employeeAfterUpdate);
        return employeeMapper.employeeToEmployeeDto(employeeAfterUpdate);
    }

    public EmployeeDto updateEmployee(EmployeeUpdateDto employeeUpdateDto) {
        LOGGER.info("updateCar(" + employeeUpdateDto + ")");
        if (employeeUpdateDto.getId() != null && employeeRepository.existsById(employeeUpdateDto.getId())) {
            Employee employee=employeeMapper.employeeUpdateDtoToEmployee(employeeUpdateDto);
            LOGGER.info("Employee successfully updated");
            return employeeMapper.employeeToEmployeeDto(employee);
        }
        LOGGER.info("Employee update failed!");
        return null;
    }

    public boolean deleteEmployee(Long id) {
        LOGGER.info("deleteCar(" + id + ")");
        if(employeeRepository.existsById(id)){
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
