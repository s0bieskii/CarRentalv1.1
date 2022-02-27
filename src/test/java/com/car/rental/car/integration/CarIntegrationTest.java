package com.car.rental.car.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.car.rental.car.Car;
import com.car.rental.car.CarService;
import com.car.rental.car.dto.CarAddDto;
import com.car.rental.car.dto.CarDto;
import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.details.dto.CarDetailsAddDto;
import com.car.rental.utils.Config;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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
public class CarIntegrationTest {

    private final CarService carService;
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    @Autowired
    public CarIntegrationTest(CarService carService, JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.carService = carService;
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
    void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "car", "car_details", "employee", "rental", "rental_cars",
                "rental_employees", "rents", "return_report", "users", "users_rents");
    }

    @Test
    void addCarMethodShouldReturnInstanceOfCar() {
        //given
        CarAddDto carToAdd = new CarAddDto();
        //when
        Car returnedCar = carService.addCar(carToAdd);
        //then
        assertInstanceOf(Car.class, returnedCar);
    }

    @Test
    void addCarMethodShouldReturnInstanceOfCarWithNotEmptyCarDetails() {
        //given
        CarAddDto carToAdd = new CarAddDto();
        //when
        Car returnedCar = carService.addCar(carToAdd);
        //then
        assertNotNull(returnedCar.getCarDetails());
    }

    @Test
    void addCarCarShouldAutoGenerateCarId() {
        //given
        CarAddDto carToAdd = new CarAddDto();
        carToAdd.setBrand("BMW");
        carToAdd.setModel("X3");
        //when
        Car car = carService.addCar(carToAdd);
        //then
        assertNotNull(car.getId());
    }

    @Test
    void createCarWithCarAddDtoShouldReturnCarWithTheSameParameters() {
        //given
        CarAddDto carToAdd = new CarAddDto();
        carToAdd.setBrand("BMW");
        carToAdd.setModel("X3");
        CarDetailsAddDto carDetailsToAdd = new CarDetailsAddDto();
        carDetailsToAdd.setColor("red");
        carDetailsToAdd.setFuel("diesel");
        carDetailsToAdd.setPrice(BigDecimal.valueOf(100.0));
        carToAdd.setCarDetails(carDetailsToAdd);
        //when
        Car car = carService.addCar(carToAdd);
        //then
        assertAll(
                () -> assertEquals(carToAdd.getBrand(), car.getBrand()),
                () -> assertEquals(carToAdd.getModel(), car.getModel()),
                () -> assertEquals(carToAdd.isAvailable(), car.isAvailable()),
                () -> assertEquals(carToAdd.isDeleted(), car.isDeleted()),
                () -> assertEquals(carToAdd.getCarDetails().getColor(), car.getCarDetails().getColor()),
                () -> assertEquals(carToAdd.getCarDetails().getFuel(), car.getCarDetails().getFuel()),
                () -> assertEquals(carToAdd.getCarDetails().getPrice(), car.getCarDetails().getPrice()),
                () -> assertNull(car.getCarDetails().getDoors()),
                () -> assertNull(car.getCarDetails().getSegment())
        );
    }

    @Test
    void searchCarByIdWhereCarWithGivenIdExistShouldReturnCarDto() {
        //given
        CarAddDto carDto = new CarAddDto();
        //when
        Car car = carService.addCar(carDto);
        CarDto carDtoFromDb = carService.findById(car.getId());
        //then
        assertNotNull(carDtoFromDb);
    }

    @Test
    void searchCarByIdWhereCarWithGivenIdExistShouldReturnInstanceOfCarDto() {
        //given
        CarAddDto carDto = new CarAddDto();
        //when
        Car car = carService.addCar(carDto);
        //then
        assertInstanceOf(Car.class, car);
    }

    @Test
    void searchCarByIdWhereCarWithGivenIdNotExistShouldReturnNull() {
        //given
        Long id = Long.MAX_VALUE;
        //when
        CarDto car = carService.findById(id);
        //then
        assertNull(car);
    }

    @Test
    void findAllMethodWithEmptyDatabaseShouldReturnEmptyCarPage() {
        //given
        PageRequest request = PageRequest.of(0, 5);
        //when
        tearDown();
        Page<CarDto> emptyCarsPage = carService.getAll(request);
        //then
        assertEquals(0, emptyCarsPage.getTotalElements());
        prepareDb();
    }

    @Test
    void findAllMethodShouldReturnCarPageWithSomeCars() {
        //given
        PageRequest request = PageRequest.of(0, 5);
        //when
        Page<CarDto> notEmptyCarsPage = carService.getAll(request);
        //then
        assertNotEquals(0, notEmptyCarsPage.getTotalElements());
    }

    @Test
    void deleteCarMethodShouldReturnTrueIfCarWithGivenIdExists() {
        //given
        CarAddDto carToAdd = new CarAddDto();
        carToAdd.setBrand("BMW");
        carToAdd.setModel("X3");
        Car carBeforeDelete = carService.addCar(carToAdd);
        //when
        boolean carWithGivenIdExist = carService.deleteCar(carBeforeDelete.getId());
        //then
        assertTrue(carWithGivenIdExist);
    }

    @Test
    void deleteCarMethodShouldReturnFalseIfCarWithGivenIdNotExists() {
        //given
        Long id = Long.MAX_VALUE;
        //when
        boolean carWithGivenIdExist = carService.deleteCar(id);
        //then
        assertFalse(carWithGivenIdExist);
    }

    @Test
    void deletedCarShouldChangeDeleteFieldToTrue() {
        //given
        CarAddDto carToAdd = new CarAddDto();
        carToAdd.setBrand("BMW");
        carToAdd.setModel("X3");
        Car carBeforeDelete = carService.addCar(carToAdd);
        CarDto carAfterDelete;
        //when
        carService.deleteCar(carBeforeDelete.getId());
        carAfterDelete = carService.findById(carBeforeDelete.getId());
        //then
        assertTrue(carAfterDelete.isDeleted());
    }

    @Test
    void deletedCarShouldChangeAvailableFieldToFalse() {
        //given
        CarAddDto carToAdd = new CarAddDto();
        carToAdd.setBrand("BMW");
        carToAdd.setModel("X3");
        Car carBeforeDelete = carService.addCar(carToAdd);
        CarDto carAfterDelete;
        //when
        carService.deleteCar(carBeforeDelete.getId());
        carAfterDelete = carService.findById(carBeforeDelete.getId());
        //then
        assertFalse(carAfterDelete.isAvailable());
    }

    @Test
    void searchMethodShouldReturnPageObjectWithSomeCarDtoObjects() {
        //given
        CarSearchDto carSearchDto = new CarSearchDto();
        Pageable pageable = PageRequest.of(0, 6);
        //when
        Page<CarDto> page = carService.search(pageable, carSearchDto);
        //then
        assertInstanceOf(CarDto.class, page.getContent().get(0));
    }

    @Test
    void searchMethodWithEmptyCarSearchDtoShouldReturnAllAvailableCarsFromDatabase() {
        //given
        CarSearchDto carSearchDto = new CarSearchDto();
        Pageable pageable = PageRequest.of(0, 6);
        int expectingCarQuantity = 6;
        //when
        Page<CarDto> page = carService.search(pageable, carSearchDto);
        //then
        assertEquals(expectingCarQuantity, page.getTotalElements());
    }

    @Test
    void searchMethodWithGivenIdFieldShouldReturnCarWithTheSameIdIfExist() {
        //given
        CarSearchDto carSearchDto = new CarSearchDto();
        Long expectingId = 1L;
        carSearchDto.setId(expectingId);
        Pageable pageable = PageRequest.of(0, 6);
        //when
        Page<CarDto> page = carService.search(pageable, carSearchDto);
        //then
        assertAll(
                () -> assertEquals(1, page.getContent().size()),
                () -> assertEquals(expectingId, page.getContent().get(0).getId())
        );
    }

    @Test
    void searchMethodWithGivenParametersWhichIsNotEqualToAnyCarInDataBaseShouldReturnEmptyPage() {
        //given
        CarSearchDto carSearchDto = new CarSearchDto();
        carSearchDto.setColor("NotExistingColorInMyDatabase");
        Pageable pageable = PageRequest.of(0, 6);
        //when
        Page<CarDto> page = carService.search(pageable, carSearchDto);
        //then
        assertEquals(0, page.getTotalElements());
    }

    @Test
    void searchMethodWithGivenBrandAndModelShouldReturnMatchingCarsDto() {
        //given
        CarSearchDto carSearchDto = new CarSearchDto();
        carSearchDto.setBrand("BMW");
        carSearchDto.setModel("X3");
        int expectingCarQuantity = 1;
        Pageable pageable = PageRequest.of(0, 6);
        //when
        Page<CarDto> page = carService.search(pageable, carSearchDto);
        //then

        assertAll(
                () -> assertEquals(expectingCarQuantity, page.getTotalElements()),
                () -> assertThat(page.getContent()).extracting("brand").contains("BMW"),
                () -> assertThat(page.getContent()).extracting("model").contains("X3")
        );
    }

    //TODO rozwikłaj tę zagadkę xdddd
    @Test
    void searchMethodWithGivenRentalAndColorShouldReturnMatchingCarsDto() {
        //given
        int rentalId = 2;
        String color = "black";
        int expectingQuantity = 2;
        CarSearchDto carSearchDto = new CarSearchDto();
        carSearchDto.setRental(rentalId);
        carSearchDto.setColor(color);
        Pageable pageable = PageRequest.of(0, 6);
        //when
        Page result = carService.search(pageable, carSearchDto);
        //then
        assertEquals(expectingQuantity, result.getTotalElements());

    }

    @Test
    void searchMethodWithGivenMinimalRegistrationYearAndMaximalPriceShouldReturnMatchingCarsDto() {
        //given
        CarSearchDto carSearchDto = new CarSearchDto();
        int year = 2018;
        BigDecimal price = BigDecimal.valueOf(111.0);
        int expectingCarQuantity = 1;
        carSearchDto.setRegistrationYear(year);
        carSearchDto.setPrice(price);
        Pageable pageable = PageRequest.of(0, 6);
        //when
        Page<CarDto> page = carService.search(pageable, carSearchDto);
        //then
        assertAll(
                () -> assertEquals(expectingCarQuantity, page.getTotalElements()),
                () -> assertEquals(year, page.getContent().get(0).getCarDetails().getRegistrationYear()),
                () -> assertTrue(price.compareTo(page.getContent().get(0).getCarDetails().getPrice()) >= 0)
        );
    }

    @Test
    void searchMethodWithGivenSegmentAndDoorsQuantityShouldReturnMatchingCarsDto() {
        //given
        CarSearchDto carSearchDto = new CarSearchDto();
        int doors = 5;
        String segment = "limousine";
        int expectingCarQuantity = 1;
        carSearchDto.setDoors(doors);
        carSearchDto.setSegment(segment);
        Pageable pageable = PageRequest.of(0, 6);
        //when
        Page<CarDto> page = carService.search(pageable, carSearchDto);
        List<Integer> doorsList = page.getContent().stream()
                .map(carDto -> carDto.getCarDetails().getDoors()).collect(Collectors.toList());
        //then
        assertAll(
                () -> assertEquals(expectingCarQuantity, page.getTotalElements()),
                () -> assertEquals(doorsList.get(0), doors),
                () -> assertThat(page.getContent()).extracting("carDetails.segment")
                        .contains(segment)
        );
    }

    @Test
    void searchMethodWithGivenSeatsAndFuelShouldReturnMatchingCarsDto() {
        //given
        CarSearchDto carSearchDto = new CarSearchDto();
        int seats = 5;
        String fuel = "diesel";
        int expectingCarQuantity = 3;
        Pageable pageable = PageRequest.of(0, 6);
        //when
        carSearchDto.setSeats(seats);
        carSearchDto.setFuel(fuel);
        Page<CarDto> page = carService.search(pageable, carSearchDto);
        List<Integer> seatsList = page.getContent().stream()
                .map(carDto -> carDto.getCarDetails().getDoors()).collect(Collectors.toList());
        //then
        assertAll(
                () -> assertEquals(expectingCarQuantity, page.getTotalElements()),
                () -> assertEquals(seats, seatsList.get(0)),
                () -> assertThat(page.getContent()).extracting("carDetails.fuel")
                        .contains(fuel)
        );
    }

    @Test
    void searchMethodWithGivenTransmissionShouldReturnMatchingCarsDto() {
        //given
        CarSearchDto carSearchDto = new CarSearchDto();
        String transmission = "automatic";
        int expectingCarQuantity = 3;
        carSearchDto.setTransmission(transmission);
        Pageable pageable = PageRequest.of(0, 6);
        //when
        Page<CarDto> page = carService.search(pageable, carSearchDto);
        //then
        assertAll(
                () -> assertEquals(expectingCarQuantity, page.getTotalElements()),
                () -> assertThat(page.getContent()).extracting("carDetails.transmission")
                        .contains(transmission)
        );
    }

    @Test
    void searchMethodWithGivenStartDateAndEndDateShouldReturnAvailableCarsInThisDateRange() {
        //given
        CarSearchDto carSearchDto = new CarSearchDto();
        LocalDateTime start = LocalDateTime.of(2022, 02, 05, 12, 30);
        LocalDateTime end = LocalDateTime.of(2022, 02, 15, 12, 30);
        int expectingCarQuantity = 5;
        carSearchDto.setStart(start);
        carSearchDto.setEnd(end);
        Pageable pageable = PageRequest.of(0, 6);
        //when
        Page<CarDto> page = carService.search(pageable, carSearchDto);
        //then
        assertAll(
                () -> assertEquals(expectingCarQuantity, page.getTotalElements())
        );
    }

    @Test
    void searchMethodWithGivenRentalIdShouldReturnAllAvailableCarsFromRental(){
        //given
        CarSearchDto carSearchDto = new CarSearchDto();
        int expectingCarQuantity = 3;
        int rentalId = 2;
        carSearchDto.setRental(rentalId);
        Pageable pageable = PageRequest.of(0, 6);
        //when
        Page<CarDto> page = carService.search(pageable, carSearchDto);
        //then
        assertAll(
                () -> assertEquals(expectingCarQuantity, page.getTotalElements())
        );
    }

    @Test
    void searchMethodWithGivenRentalIdAndStartDateAndEndDateShouldReturnAvailableCarsInThisDateRange() {
        //given
        CarSearchDto carSearchDto = new CarSearchDto();
        LocalDateTime start = LocalDateTime.of(2022, 02, 05, 12, 30);
        LocalDateTime end = LocalDateTime.of(2022, 02, 15, 12, 30);
        int rentalId = 2;
        int expectingCarQuantity = 2;
        carSearchDto.setStart(start);
        carSearchDto.setEnd(end);
        carSearchDto.setRental(rentalId);
        Pageable pageable = PageRequest.of(0, 6);
        //when
        Page<CarDto> page = carService.search(pageable, carSearchDto);
        //then
        assertAll(
                () -> assertEquals(expectingCarQuantity, page.getTotalElements())
        );
    }

    @Test
    void searchMethodWithGivenCarIdAndStartDateAndEndDateShouldReturnAvailableCarsInThisDateRangeHavingAgainRentalDelayFalseAttempt() {
        //given
        int delayInHours = Config.timeDelayUntilNextRent;
        CarSearchDto carSearchDto = new CarSearchDto();
        LocalDateTime start = LocalDateTime.of(2022, 02, 15, 12, 29);
        LocalDateTime end = LocalDateTime.of(2022, 02, 20, 12, 30);
        Long carId = 5L;
        int expectingCarQuantity = 0;
        carSearchDto.setStart(start);
        carSearchDto.setEnd(end);
        carSearchDto.setId(carId);
        Pageable pageable = PageRequest.of(0, 6);
        //when
        Page<CarDto> page = carService.search(pageable, carSearchDto);
        //then
        assertAll(
                () -> assertEquals(expectingCarQuantity, page.getTotalElements())
        );
    }

    @Test
    void searchMethodWithGivenCarIdAndStartDateAndEndDateShouldReturnAvailableCarsInThisDateRangeHavingAgainRentalDelayTrueAttempt() {
        //given
        int delayInHours = Config.timeDelayUntilNextRent;
        CarSearchDto carSearchDto = new CarSearchDto();
        LocalDateTime start = LocalDateTime.of(2022, 02, 15, 12, 30).plusHours(delayInHours);
        LocalDateTime end = LocalDateTime.of(2022, 02, 20, 12, 30);
        Long carId = 5L;
        int expectingCarQuantity = 1;
        carSearchDto.setStart(start);
        carSearchDto.setEnd(end);
        carSearchDto.setId(carId);
        Pageable pageable = PageRequest.of(0, 6);
        //when
        Page<CarDto> page = carService.search(pageable, carSearchDto);
        //then
        assertAll(
                () -> assertEquals(expectingCarQuantity, page.getTotalElements())
        );
    }

}
