package com.car.rental.car.integration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.car.rental.rental.Rental;
import com.car.rental.rental.RentalService;
import com.car.rental.rental.dto.RentalAddDto;
import com.car.rental.rental.dto.RentalDto;
import com.car.rental.rental.dto.RentalSearchDto;
import com.car.rental.rental.dto.RentalUpdateDto;
import com.car.rental.rental.dto.RentalWithoutEmployeeDto;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.jdbc.JdbcTestUtils;

@SpringBootTest
public class RentalIntegrationTest {

    private final RentalService rentalService;
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public RentalIntegrationTest(RentalService rentalService, JdbcTemplate jdbcTemplate,
                                 DataSource dataSource) {
        this.rentalService = rentalService;
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
    void addRentalMethodShouldReturnInstanceOfRental() {
        //given
        RentalAddDto rentalAddDto = new RentalAddDto();
        //when
        Rental rental = rentalService.addRental(rentalAddDto);
        //then
        assertInstanceOf(Rental.class, rental);
    }

    @Test
    void addRentalMethodShouldAutoIncrementId() {
        //given
        RentalAddDto rentalAddDto = new RentalAddDto();
        //when
        Rental rental = rentalService.addRental(rentalAddDto);
        //then
        assertNotNull(rental.getId());
    }

    @Test
    void gerAllMethodShouldReturnPageWithGivenPageAndSize() {
        //given
        int pageNumber = 1;
        int size = 6;
        PageRequest page = PageRequest.of(pageNumber, size);
        //when
        Page rentals = rentalService.getAll(page);
        //then
        assertAll(
                () -> assertEquals(pageNumber, page.getPageNumber()),
                () -> assertEquals(size, page.getPageSize())
        );
    }

    @Test
    void getAllMethodShouldReturnPageWithAllRentalInDatabase() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        int expectedRentalsQuantity = 3;
        //when
        Page rentals = rentalService.getAll(page);
        //then
        assertEquals(expectedRentalsQuantity, rentals.getTotalElements());
    }

    @Test
    void getAllMethodShouldReturnPageWithEmptyDatabaseShouldReturnEmptyPage() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        int expectedRentalsQuantity = 0;
        //when
        cleanDb();
        Page rentals = rentalService.getAll(page);
        //then
        assertEquals(expectedRentalsQuantity, rentals.getTotalElements());
        prepareDb();
    }

    @Test
    void findByIdMethodShouldReturnNullIfRentalWithGivenIdNotSexist() {
        //given
        Long id = Long.MAX_VALUE;
        //when
        RentalDto rental = rentalService.findById(id);
        //then
        assertNull(rental);
    }

    @Test
    void findByIdMethodShouldReturnRentalWithGivenId() {
        //given
        Long id = 1L;
        //when
        RentalDto rental = rentalService.findById(id);
        //then
        assertEquals(id, rental.getId());
    }

    @Test
    void searchMethodShouldReturnPageRenalWithGivenId() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentalSearchDto searchDto = new RentalSearchDto();
        Long id = 1L;
        int expectingQuantity = 1;
        //when
        searchDto.setId(id);
        Page<RentalWithoutEmployeeDto> rentals = rentalService.search(page, searchDto);
        //then
        assertAll(
                () -> assertEquals(expectingQuantity, rentals.getTotalElements()),
                () -> assertEquals(id, rentals.getContent().get(0).getId())
        );
    }

    @Test
    void searchMethodShouldReturnPageRenalWithGivenCountry() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentalSearchDto searchDto = new RentalSearchDto();
        String country = "Poland";
        int expectingQuantity = 3;
        //when
        searchDto.setCountry(country);
        Page<RentalWithoutEmployeeDto> rentals = rentalService.search(page, searchDto);
        //then
        assertAll(
                () -> assertEquals(expectingQuantity, rentals.getTotalElements()),
                () -> assertEquals(country.toLowerCase(), rentals.getContent().get(0).getCountry().toLowerCase()),
                () -> assertEquals(country.toLowerCase(), rentals.getContent().get(1).getCountry().toLowerCase()),
                () -> assertEquals(country.toLowerCase(), rentals.getContent().get(2).getCountry().toLowerCase())
        );
    }

    @Test
    void searchMethodShouldReturnPageRenalWithGivenCountryAndCity() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentalSearchDto searchDto = new RentalSearchDto();
        String country = "Poland";
        String city = "Poznan";
        int expectingQuantity = 1;
        //when
        searchDto.setCountry(country);
        searchDto.setCity(city);
        Page<RentalWithoutEmployeeDto> rentals = rentalService.search(page, searchDto);
        //then
        assertAll(
                () -> assertEquals(expectingQuantity, rentals.getTotalElements()),
                () -> assertEquals(country.toLowerCase(), rentals.getContent().get(0).getCountry().toLowerCase()),
                () -> assertEquals(city.toLowerCase(), rentals.getContent().get(0).getCity().toLowerCase())
        );
    }

    @Test
    void searchMethodShouldReturnPageRenalWithGivenPhone() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentalSearchDto searchDto = new RentalSearchDto();
        String phone = "997 997 997";
        int expectingQuantity = 1;
        //when
        searchDto.setPhone(phone);
        Page<RentalWithoutEmployeeDto> rentals = rentalService.search(page, searchDto);
        //then
        assertAll(
                () -> assertEquals(expectingQuantity, rentals.getTotalElements()),
                () -> assertEquals(phone.toLowerCase(), rentals.getContent().get(0).getPhone().toLowerCase())
        );
    }

    @Test
    void searchMethodShouldReturnPageRenalWithGivenPostcode() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentalSearchDto searchDto = new RentalSearchDto();
        String postCode = "61-810";
        int expectingQuantity = 1;
        //when
        searchDto.setPostCode(postCode);
        Page<RentalWithoutEmployeeDto> rentals = rentalService.search(page, searchDto);
        //then
        assertAll(
                () -> assertEquals(expectingQuantity, rentals.getTotalElements()),
                () -> assertEquals(postCode.toLowerCase(), rentals.getContent().get(0).getPostCode().toLowerCase())
        );
    }

    @Test
    void searchMethodShouldReturnPageRenalWithGivenStreet() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentalSearchDto searchDto = new RentalSearchDto();
        String street = "Poznanska 11";
        int expectingQuantity = 1;
        //when
        searchDto.setStreet(street);
        Page<RentalWithoutEmployeeDto> rentals = rentalService.search(page, searchDto);
        //then
        assertAll(
                () -> assertEquals(expectingQuantity, rentals.getTotalElements()),
                () -> assertEquals(street.toLowerCase(), rentals.getContent().get(0).getStreet().toLowerCase())
        );
    }

    @Test
    void searchMethodShouldReturnEmptyPageRenalWhenGivenValuesMatchAnyRental() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentalSearchDto searchDto = new RentalSearchDto();
        String street = "NOT EXISTING STREET";
        int expectingQuantity = 0;
        //when
        searchDto.setStreet(street);
        Page<RentalWithoutEmployeeDto> rentals = rentalService.search(page, searchDto);
        //then
        assertEquals(expectingQuantity, rentals.getTotalElements());
    }

    @Test
    void searchMethodShouldReturnAllRentalsWhenValuesAreEmpty() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentalSearchDto searchDto = new RentalSearchDto();
        int expectingQuantity = 3;
        //when
        Page<RentalWithoutEmployeeDto> rentals = rentalService.search(page, searchDto);
        //then
        assertEquals(expectingQuantity, rentals.getTotalElements());
    }

    @Test
    void searchMethodShouldNotReturnRentalsWhenIsDeleted() {
        //given
        PageRequest page = PageRequest.of(0, 6);
        RentalSearchDto searchDto = new RentalSearchDto();
        searchDto.setId(1L);
        rentalService.deleteRental(1L);
        int expectingQuantity = 0;
        //when
        Page<RentalWithoutEmployeeDto> rentals = rentalService.search(page, searchDto);
        //then
        assertEquals(expectingQuantity, rentals.getTotalElements());
    }

    @Test
    void updateRentalMethodShouldReturnNullIfRentalWithGivenIdNotExist() {
        //given
        RentalUpdateDto updateDto = new RentalUpdateDto();
        Long id = Long.MAX_VALUE;
        updateDto.setId(id);
        //when
        RentalDto updatedRental = rentalService.updateRental(updateDto);
        //then
        assertNull(updatedRental);
    }

    @Test
    void updateRentalMethodShouldReturnRentalWithUpdatedCity() {
        //given
        RentalUpdateDto updateDto = new RentalUpdateDto();
        Long id = 1L;
        String newCity = "Bydgoszcz";
        String oldCity = rentalService.findById(id).getCity();
        updateDto.setId(id);
        updateDto.setCity(newCity);
        //when
        RentalDto updatedRental = rentalService.updateRental(updateDto);
        //then
        assertAll(
                () -> assertEquals(id, updatedRental.getId()),
                () -> assertNotEquals(oldCity, updatedRental.getCity()),
                () -> assertEquals(newCity, updatedRental.getCity())
        );
    }

    @Test
    void updateRentalMethodShouldReturnRentalWithUpdatedFieldsButWhenSomeFieldIsEmptyShouldBeUpdatedToNull() {
        //given
        RentalUpdateDto updateDto = new RentalUpdateDto();
        Long id = 1L;
        String newCity = "Bydgoszcz";
        String oldCity = rentalService.findById(id).getCity();
        String oldCountry = rentalService.findById(id).getCountry();
        updateDto.setId(id);
        updateDto.setCity(newCity);
        //when
        RentalDto updatedRental = rentalService.updateRental(updateDto);
        //then
        assertAll(
                () -> assertEquals(id, updatedRental.getId()),
                () -> assertNotEquals(oldCity, updatedRental.getCity()),
                () -> assertEquals(newCity, updatedRental.getCity()),
                () -> assertNotEquals(oldCountry, updatedRental.getCountry()),
                () -> assertNull(updatedRental.getCountry())
        );
    }

    @Test
    void deleteRentalMethodShouldReturnFalseWhenRentalWithGivenIdNotExist() {
        //given
        Long id = Long.MAX_VALUE;
        //when
        boolean isDeleted = rentalService.deleteRental(id);
        //then
        assertFalse(isDeleted);
    }

    @Test
    void deleteRentalMethodShouldReturnTrueWhenRentalWithGivenIdIsSuccessfulDeleted() {
        //given
        Long id = 1L;
        //when
        boolean isDeleted = rentalService.deleteRental(id);
        //then
        assertTrue(isDeleted);
    }

    @Test
    void deleteRentalMethodShouldSetDeleteFieldTrueWhenRentalWithGivenIdIsSuccessfulDeleted() {
        //given
        Long id = 1L;
        RentalDto rentalBefore = rentalService.findById(id);
        boolean beforeDelete = rentalBefore.isDeleted();
        //when
        rentalService.deleteRental(id);
        boolean afterDelete = rentalService.findById(id).isDeleted();
        //then
        assertNotEquals(beforeDelete, afterDelete);
        assertTrue(afterDelete);
    }

}
