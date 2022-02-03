package com.car.rental.car.integration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.car.rental.employee.Employee;
import com.car.rental.employee.EmployeeService;
import com.car.rental.employee.dto.EmployeeAddDto;
import com.car.rental.employee.dto.EmployeeDto;
import com.car.rental.employee.dto.EmployeeSearchDto;
import com.car.rental.employee.dto.EmployeeUpdateDto;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.jdbc.JdbcTestUtils;

@SpringBootTest
public class EmployeeIntegrationTest {

    private final EmployeeService employeeService;
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public EmployeeIntegrationTest(EmployeeService employeeService, JdbcTemplate jdbcTemplate,
                                   DataSource dataSource) {
        this.employeeService = employeeService;
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    void prepareDb() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false,
                "UTF-8", new ClassPathResource("data.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }

    @EventListener(ContextRefreshedEvent.class)
    void startWithClear() {
        cleanDb();
    }

    @AfterEach
    void cleanDb() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "car", "car_details", "employee", "rental", "rental_cars",
                "rental_employees", "rents", "return_report", "users", "users_rents");
    }

    @Test
    void addEmployeeShouldReturnInstanceOfEmployee() {
        //given
        EmployeeAddDto employeeToAdd = new EmployeeAddDto();
        //when
        Employee employee = employeeService.addEmployee(employeeToAdd);
        //then
        assertInstanceOf(Employee.class, employee);
    }

    @Test
    void addEmployeeShouldAutoGenerateId() {
        //given
        EmployeeAddDto employeeToAdd = new EmployeeAddDto();
        //when
        Employee employee = employeeService.addEmployee(employeeToAdd);
        //then
        assertNotNull(employee.getId());
    }

    @Test
    void addEmployeeShouldReturnEmployeeWithTheSameFieldsValue() {
        //given
        EmployeeAddDto employeeToAdd = new EmployeeAddDto();
        String firstName = "John";
        String lastName = "LOL";
        employeeToAdd.setFirstName(firstName);
        employeeToAdd.setLastName(lastName);
        //when
        Employee employee = employeeService.addEmployee(employeeToAdd);
        EmployeeDto savedEmployee = employeeService.findById(employee.getId());
        //then
        assertAll(
                () -> assertEquals(firstName, savedEmployee.getFirstName()),
                () -> assertEquals(lastName, savedEmployee.getLastName())
        );
    }

    @Test
    void getAllMethodShouldReturnPageWithEmployeeDto() {
        //given
        prepareDb();
        PageRequest request = PageRequest.of(0, 5);
        //when
        Page<EmployeeDto> employee = employeeService.getAll(request);
        //then
        assertNotEquals(employee.getTotalElements(), 0);
    }

    @Test
    void getAllMethodWithEmptyDatabaseShouldReturnEmptyPage() {
        //given
        PageRequest request = PageRequest.of(0, 5);
        //when
        cleanDb();
        Page<EmployeeDto> employee = employeeService.getAll(request);
        //then
        assertEquals(0, employee.getTotalElements());
    }

    @Test
    void getAllMethodWithGivenPageSizeAndPageShouldReturnPageWithTheSameSizeAndPageValue() {
        //given
        prepareDb();
        PageRequest request = PageRequest.of(1, 1);
        //when
        Page<EmployeeDto> employee = employeeService.getAll(request);
        //then
        assertAll(
                () -> assertEquals(request.getPageSize(), employee.getPageable().getPageSize()),
                () -> assertEquals(request.getPageNumber(), employee.getPageable().getPageNumber())
        );
    }

    @Test
    void findByIdWithNotExistsEmployeeIdShouldReturnNull() {
        //given
        prepareDb();
        Long notExistingId = Long.MAX_VALUE;
        //when
        EmployeeDto employee = employeeService.findById(notExistingId);
        //then
        assertNull(employee);
    }

    @Test
    void findByIdWithExistsEmployeeIdShouldReturnEmployeeDtoWithTheSameId() {
        //given
        prepareDb();
        Long existingId = 3L;
        //when
        EmployeeDto employee = employeeService.findById(existingId);
        //then
        assertAll(
                () -> assertInstanceOf(EmployeeDto.class, employee),
                () -> assertEquals(employee.getId(), existingId)
        );
    }

    @Test
    void searchMethodShouldReturnPageEmployeeDto() {
        //given
        prepareDb();
        PageRequest page = PageRequest.of(0, 5);
        EmployeeSearchDto employeeSearchDto = new EmployeeSearchDto();
        //when
        Page<EmployeeDto> employeeDtoPage = employeeService.search(page, employeeSearchDto);
        //then
        assertAll(
                () -> assertInstanceOf(Page.class, employeeDtoPage),
                () -> assertEquals(EmployeeDto.class, employeeDtoPage.getContent().get(0).getClass())
        );
    }

    @Test
    void searchMethodShouldReturnAllEmployeeWhenEmployeeSearchDtoFieldsIsNull() {
        //given
        prepareDb();
        PageRequest page = PageRequest.of(0, 5);
        EmployeeSearchDto employeeSearchDto = new EmployeeSearchDto();
        Long totalEmployeeInDatabase = 7L;
        //when
        Page<EmployeeDto> employeeDtoPage = employeeService.search(page, employeeSearchDto);
        //then
        assertEquals(totalEmployeeInDatabase, employeeDtoPage.getTotalElements());
    }

    @Test
    void searchMethodShouldReturnEmptyPageWithEmployeeDtoWhenGivenFirstNameNotExistInDatabase() {
        //given
        prepareDb();
        PageRequest page = PageRequest.of(0, 5);
        EmployeeSearchDto employeeSearchDto = new EmployeeSearchDto();
        employeeSearchDto.setFirstName("Loremipsumloremipsum");
        Long totalEmployeeInDatabase = 0L;
        //when
        Page<EmployeeDto> employeeDtoPage = employeeService.search(page, employeeSearchDto);
        //then
        assertEquals(totalEmployeeInDatabase, employeeDtoPage.getTotalElements());
    }

    @Test
    void searchMethodShouldReturnOneEmployeeDtoWhenGivenFirstNameMatchWithOneEmployeeInDatabase() {
        //given
        prepareDb();
        PageRequest page = PageRequest.of(0, 5);
        EmployeeSearchDto employeeSearchDto = new EmployeeSearchDto();
        employeeSearchDto.setFirstName("Kaska");
        Long totalEmployeeMatchingToValuesInDatabase = 1L;
        //when
        Page<EmployeeDto> employeeDtoPage = employeeService.search(page, employeeSearchDto);
        //then
        assertEquals(totalEmployeeMatchingToValuesInDatabase, employeeDtoPage.getTotalElements());
    }

    @Test
    void searchMethodShouldReturnAllEmployeeDtoMatchingToGivenFirstName() {
        //given
        prepareDb();
        PageRequest page = PageRequest.of(0, 5);
        EmployeeSearchDto employeeSearchDto = new EmployeeSearchDto();
        employeeSearchDto.setFirstName("Patryk");
        Long totalEmployeeMatchingToValuesInDatabase = 2L;
        //when
        Page<EmployeeDto> employeeDtoPage = employeeService.search(page, employeeSearchDto);
        //then
        assertEquals(totalEmployeeMatchingToValuesInDatabase, employeeDtoPage.getTotalElements());
    }

    @Test
    void searchMethodWithGivenFirstNameShouldReturnMatchingEmployeeDto() {
        //given
        prepareDb();
        PageRequest page = PageRequest.of(0, 5);
        EmployeeSearchDto employeeSearchDto = new EmployeeSearchDto();
        String firstName = "Patryk";
        employeeSearchDto.setFirstName(firstName);
        Long totalEmployeeMatchingToValuesInDatabase = 2L;
        //when
        Page<EmployeeDto> employeeDtoPage = employeeService.search(page, employeeSearchDto);
        //then
        assertAll(
                () -> assertEquals(totalEmployeeMatchingToValuesInDatabase, employeeDtoPage.getContent().size()),
                () -> assertEquals(firstName, employeeDtoPage.getContent().get(0).getFirstName()),
                () -> assertEquals(firstName, employeeDtoPage.getContent().get(1).getFirstName())
        );
    }

    @Test
    void searchMethodWithGivenFirstNameAndLastNameShouldReturnEmployeeDtoMatchingToVGivenValues() {
        //given
        prepareDb();
        PageRequest page = PageRequest.of(0, 5);
        EmployeeSearchDto employeeSearchDto = new EmployeeSearchDto();
        String firstName = "Patryk";
        String lastName = "Chojnacki";
        employeeSearchDto.setFirstName(firstName);
        employeeSearchDto.setLastName(lastName);
        Long totalEmployeeMatchingToValuesInDatabase = 1L;
        //when
        Page<EmployeeDto> employeeDtoPage = employeeService.search(page, employeeSearchDto);
        //then
        assertAll(
                () -> assertEquals(totalEmployeeMatchingToValuesInDatabase, employeeDtoPage.getContent().size()),
                () -> assertEquals(firstName, employeeDtoPage.getContent().get(0).getFirstName()),
                () -> assertEquals(lastName, employeeDtoPage.getContent().get(0).getLastName())
        );
    }

    @Test
    void searchMethodWithGivenEmploymentPositionShouldReturnEmployeeDtoMatchingToVGivenValuesButWithOutEmployeesMarkedAsDeleted() {
        //given
        prepareDb();
        PageRequest page = PageRequest.of(0, 5);
        EmployeeSearchDto employeeSearchDto = new EmployeeSearchDto();
        String employmentPosition = "dealer";
        employeeSearchDto.setEmploymentPosition(employmentPosition);
        Long totalEmployeeMatchingToValuesInDatabase = 5L;
        //when
        Page<EmployeeDto> employeeDtoPage = employeeService.search(page, employeeSearchDto);
        //then
        assertAll(
                () -> assertEquals(totalEmployeeMatchingToValuesInDatabase, employeeDtoPage.getContent().size()),
                () -> assertEquals(employmentPosition, employeeDtoPage.getContent().get(0).getEmploymentPosition()),
                () -> assertEquals(employmentPosition, employeeDtoPage.getContent().get(1).getEmploymentPosition()),
                () -> assertEquals(employmentPosition, employeeDtoPage.getContent().get(2).getEmploymentPosition()),
                () -> assertEquals(employmentPosition, employeeDtoPage.getContent().get(3).getEmploymentPosition()),
                () -> assertEquals(employmentPosition, employeeDtoPage.getContent().get(4).getEmploymentPosition())
        );
    }

    @Test
    void searchMethodWithGivenIdShouldReturnEmployeeDtoMatchingToVGivenId() {
        //given
        prepareDb();
        PageRequest page = PageRequest.of(0, 5);
        EmployeeSearchDto employeeSearchDto = new EmployeeSearchDto();
        Long id = 1L;
        Long totalEmployeeMatchingToValuesInDatabase = 1L;
        employeeSearchDto.setId(id);
        //when
        Page<EmployeeDto> employeeDtoPage = employeeService.search(page, employeeSearchDto);
        //then
        assertAll(
                () -> assertEquals(totalEmployeeMatchingToValuesInDatabase, employeeDtoPage.getContent().size()),
                () -> assertEquals(id, employeeDtoPage.getContent().get(0).getId())
        );
    }

    @Test
    void searchMethodWithGivenManyFieldsWhichNotMatchingToAnyShouldReturnEmptyPage() {
        //given
        prepareDb();
        PageRequest page = PageRequest.of(0, 5);
        EmployeeSearchDto employeeSearchDto = new EmployeeSearchDto();
        String firstName = "Patryk";
        String lastName = "NotExistingLastnmae";
        employeeSearchDto.setFirstName(firstName);
        employeeSearchDto.setLastName(lastName);
        Long totalEmployeeMatchingToValuesInDatabase = 0L;
        //when
        Page<EmployeeDto> employeeDtoPage = employeeService.search(page, employeeSearchDto);
        //then
        assertEquals(totalEmployeeMatchingToValuesInDatabase, employeeDtoPage.getContent().size());
    }

    @Test
    void updateEmployeeByReflectionReturnNullWhenEmployeeWithGivenIdInEmployeeSearchDtoNotExists() {
        //given
        prepareDb();
        Long id = Long.MAX_VALUE;
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(id);
        //when
        EmployeeDto returnedEmployee = employeeService.updateEmployeeByReflection(employeeUpdateDto);
        //then
        assertNull(returnedEmployee);
    }

    @Test
    void updateEmployeeByReflectionReturnNullWhenEmployeeUpdateDtoIdIsNull() {
        //given
        prepareDb();
        Long id = null;
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(id);
        //when
        EmployeeDto returnedEmployee = employeeService.updateEmployeeByReflection(employeeUpdateDto);
        //then
        assertNull(returnedEmployee);
    }

    @Test
    void updateEmployeeByReflectionReturnUpdatedEmployeeDtoWithNewGivenFirstName() {
        //given
        prepareDb();
        Long id = 1L;
        String name = "Bossman";
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(id);
        employeeUpdateDto.setFirstName(name);
        //when
        EmployeeDto employeeBeforeUpdate = employeeService.findById(id);
        String nameBeforeUpdate = employeeBeforeUpdate.getFirstName();
        EmployeeDto returnedEmployee = employeeService.updateEmployeeByReflection(employeeUpdateDto);
        //then
        assertAll(
                () -> assertEquals(name, returnedEmployee.getFirstName()),
                () -> assertNotEquals(nameBeforeUpdate, returnedEmployee.getFirstName())
        );
    }

    @Test
    void updateEmployeeByReflectionReturnUpdatedEmployeeDtoWithNewFirstNameAndLastName() {
        //given
        prepareDb();
        Long id = 1L;
        String name = "Bossman";
        String lastName = "Kaka";
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(id);
        employeeUpdateDto.setFirstName(name);
        employeeUpdateDto.setLastName(lastName);
        //when
        EmployeeDto employeeBeforeUpdate = employeeService.findById(id);
        String nameBeforeUpdate = employeeBeforeUpdate.getFirstName();
        String lastNameBeforeUpdate = employeeBeforeUpdate.getFirstName();
        EmployeeDto returnedEmployee = employeeService.updateEmployeeByReflection(employeeUpdateDto);
        //then
        assertAll(
                () -> assertEquals(name, returnedEmployee.getFirstName()),
                () -> assertNotEquals(nameBeforeUpdate, returnedEmployee.getFirstName()),
                () -> assertEquals(lastName, returnedEmployee.getLastName()),
                () -> assertNotEquals(lastNameBeforeUpdate, returnedEmployee.getLastName())
        );
    }

    @Test
    void updateEmployeeByReflectionReturnUpdatedEmployeeDtoWithNewEmploymentPosition() {
        //given
        prepareDb();
        Long id = 1L;
        String newEmploymentPosition = "dealer";
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(id);
        employeeUpdateDto.setEmploymentPosition(newEmploymentPosition);
        //when
        EmployeeDto employeeBeforeUpdate = employeeService.findById(id);
        EmployeeDto returnedEmployee = employeeService.updateEmployeeByReflection(employeeUpdateDto);
        //then
        assertAll(
                () -> assertEquals(newEmploymentPosition, returnedEmployee.getEmploymentPosition()),
                () -> assertNotEquals(employeeBeforeUpdate, returnedEmployee.getEmploymentPosition())
        );
    }

    @Test
    void updateEmployeeReturnNullIfEmployeeWithGivenIdNotExist() {
        //given
        prepareDb();
        Long id = Long.MAX_VALUE;
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(id);
        //when
        EmployeeDto employee = employeeService.updateEmployee(employeeUpdateDto);
        //then
        assertNull(employee);
    }

    @Test
    void updateEmployeeReturnNullIfEmployeeWithGivenNullId() {
        //given
        prepareDb();
        Long id = null;
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(id);
        //when
        EmployeeDto employee = employeeService.updateEmployee(employeeUpdateDto);
        //then
        assertNull(employee);
    }

    @Test
    void updateEmployeeReturnEmployeeDtoWithNewFirstName() {
        //given
        prepareDb();
        Long id = 1L;
        String newFirstName="Popek";
        String oldFirstName;
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(id);
        employeeUpdateDto.setFirstName(newFirstName);
        EmployeeDto rootEmployee=employeeService.findById(id);
        oldFirstName=rootEmployee.getFirstName();
        //when
        EmployeeDto employee = employeeService.updateEmployee(employeeUpdateDto);
        //then
        assertAll(
                ()->assertNotEquals(oldFirstName, employee.getFirstName()),
                ()->assertEquals(newFirstName, employee.getFirstName())
        );
    }

    @Test
    void updateEmployeeReturnEmployeeDtoWithNewFirstNameAndLastName() {
        //given
        prepareDb();
        Long id = 1L;
        String newFirstName="Popek";
        String newLastName="Karkaczow";
        String oldFirstName;
        String oldLastName;
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(id);
        employeeUpdateDto.setFirstName(newFirstName);
        employeeUpdateDto.setLastName(newLastName);
        EmployeeDto rootEmployee=employeeService.findById(id);
        oldFirstName=rootEmployee.getFirstName();
        oldLastName=rootEmployee.getLastName();
        //when
        EmployeeDto employee = employeeService.updateEmployee(employeeUpdateDto);
        //then
        assertAll(
                ()->assertNotEquals(oldFirstName, employee.getFirstName()),
                ()->assertEquals(newFirstName, employee.getFirstName()),
                ()->assertNotEquals(oldLastName, employee.getLastName()),
                ()->assertEquals(newLastName, employee.getLastName())
        );
    }

    @Test
    void updateEmployeeReturnEmployeeDtoWithNewEmploymentPositionAndSalaryPerHourField() {
        //given
        prepareDb();
        Long id = 1L;
        String newEmploymentPosition="bigbosss";
        String oldEmploymentPosition;
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(id);
        employeeUpdateDto.setEmploymentPosition(newEmploymentPosition);
        EmployeeDto rootEmployee=employeeService.findById(id);
        oldEmploymentPosition=rootEmployee.getFirstName();
        //when
        EmployeeDto employee = employeeService.updateEmployee(employeeUpdateDto);
        //then
        assertAll(
                ()->assertNotEquals(oldEmploymentPosition, employee.getEmploymentPosition()),
                ()->assertEquals(newEmploymentPosition, employee.getEmploymentPosition())
        );
    }
}