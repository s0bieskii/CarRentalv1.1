package com.car.rental.integration;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @BeforeEach
    void prepareDb() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false,
                "UTF-8", new ClassPathResource("data.sql"));
        resourceDatabasePopulator.execute(dataSource);
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
        Long notExistingId = Long.MAX_VALUE;
        //when
        EmployeeDto employee = employeeService.findById(notExistingId);
        //then
        assertNull(employee);
    }

    @Test
    void findByIdWithExistsEmployeeIdShouldReturnEmployeeDtoWithTheSameId() {
        //given
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
    void updateEmployeeReturnNullIfEmployeeWithGivenIdNotExist() {
        //given
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
        Long id = 1L;
        String newFirstName = "Popek";
        String oldFirstName;
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(id);
        employeeUpdateDto.setFirstName(newFirstName);
        EmployeeDto rootEmployee = employeeService.findById(id);
        oldFirstName = rootEmployee.getFirstName();
        //when
        EmployeeDto employee = employeeService.updateEmployee(employeeUpdateDto);
        //then
        assertAll(
                () -> assertNotEquals(oldFirstName, employee.getFirstName()),
                () -> assertEquals(newFirstName, employee.getFirstName())
        );
    }

    @Test
    void updateEmployeeReturnEmployeeDtoWithNewFirstNameAndLastName() {
        //given
        Long id = 1L;
        String newFirstName = "Popek";
        String newLastName = "Karkaczow";
        String oldFirstName;
        String oldLastName;
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(id);
        employeeUpdateDto.setFirstName(newFirstName);
        employeeUpdateDto.setLastName(newLastName);
        EmployeeDto rootEmployee = employeeService.findById(id);
        oldFirstName = rootEmployee.getFirstName();
        oldLastName = rootEmployee.getLastName();
        //when
        EmployeeDto employee = employeeService.updateEmployee(employeeUpdateDto);
        //then
        assertAll(
                () -> assertNotEquals(oldFirstName, employee.getFirstName()),
                () -> assertEquals(newFirstName, employee.getFirstName()),
                () -> assertNotEquals(oldLastName, employee.getLastName()),
                () -> assertEquals(newLastName, employee.getLastName())
        );
    }

    @Test
    void updateEmployeeReturnEmployeeDtoWithNewEmploymentPositionAndSalaryPerHourField() {
        //given
        Long id = 1L;
        String newEmploymentPosition = "bigbosss";
        String oldEmploymentPosition;
        EmployeeUpdateDto employeeUpdateDto = new EmployeeUpdateDto();
        employeeUpdateDto.setId(id);
        employeeUpdateDto.setEmploymentPosition(newEmploymentPosition);
        EmployeeDto rootEmployee = employeeService.findById(id);
        oldEmploymentPosition = rootEmployee.getFirstName();
        //when
        EmployeeDto employee = employeeService.updateEmployee(employeeUpdateDto);
        //then
        assertAll(
                () -> assertNotEquals(oldEmploymentPosition, employee.getEmploymentPosition()),
                () -> assertEquals(newEmploymentPosition, employee.getEmploymentPosition())
        );
    }

    @Test
    void searchEmployeeWithGivenERentalIdShouldReturnAllEmployeeFromRental() {
        //given
        int expectQuantity = 4;
        Long rentalId = 1L;
        EmployeeSearchDto employeeSearchDto = new EmployeeSearchDto();
        employeeSearchDto.setRentalId(rentalId);
        Pageable page = PageRequest.of(0, 6);
        //when
        Page employee = employeeService.search(page, employeeSearchDto);
        //then
        assertAll(
                () -> assertEquals(expectQuantity, employee.getContent().size())
        );
    }
}