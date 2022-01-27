package com.car.rental.car.integration;

import com.car.rental.car.Car;
import com.car.rental.car.CarService;
import com.car.rental.car.dto.CarAddDto;
import com.car.rental.car.dto.CarDto;
import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.car.dto.CarUpdateDto;
import com.car.rental.details.dto.CarDetailsAddDto;
import com.car.rental.details.dto.CarDetailsUpdateDto;
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

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CarIntegrationTest {
    @Autowired
    private CarService carService;
    @Autowired
        private JdbcTemplate jdbcTemplate;
    @Autowired
        private DataSource dataSource;

    @BeforeEach
    void prepareDb(){
        ResourceDatabasePopulator resourceDatabasePopulator=new ResourceDatabasePopulator(false, false,
                "UTF-8", new ClassPathResource("data.sql"));
        resourceDatabasePopulator.execute(dataSource);
    }

    @AfterEach
    void tearDown(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "car", "car_details", "employee", "rental", "rental_cars",
                                                    "rental_employees", "rents", "return_report", "users", "users_rents");
    }

    private void prepareDatabase(){
        //given
        CarAddDto carToAdd1=new CarAddDto();
        carToAdd1.setBrand("Ford");
        carToAdd1.setModel("Fiesta");
        carToAdd1.setCarDetails(new CarDetailsAddDto("1J4GL58K83W563804", "black", 2001, 100.0, "coupe",
                2, 2, "8V95545", 100000, LocalDate.of(2022, 05,12),
                LocalDate.of(2022, 05,12), "DIESEL", 10.0, "AUTOMATIC"));
        CarAddDto carToAdd2=new CarAddDto();
        carToAdd1.setBrand("Ford");
        carToAdd1.setModel("Kugar");
        carToAdd2.setCarDetails(new CarDetailsAddDto("1G1ZC5EB8AF171687", "red", 2010, 170.0, "sedan",
                5, 5, "FKP2457", 70000, LocalDate.of(2023, 12,7),
                LocalDate.of(2023, 12,7), "PETROL", 8.0, "MANUAL"));
        CarAddDto carToAdd3=new CarAddDto();
        carToAdd1.setBrand("BMW");
        carToAdd1.setModel("F11");
        carToAdd3.setCarDetails(new CarDetailsAddDto("1FA6P8CF8F5395244", "grey", 2019, 105.0, "coupe",
                2, 5, "Z85538", 100000, LocalDate.of(2022, 10,7),
                LocalDate.of(2022, 10,7), "DIESEL", 9.0, "AUTOMATIC"));
        CarAddDto carToAdd4=new CarAddDto();
        carToAdd1.setBrand("Opel");
        carToAdd1.setModel("Corsa");
        carToAdd4.setCarDetails(new CarDetailsAddDto("1FTCR14T5JPB23671", "black", 2020, 115.0, "limousine",
                5, 5, "VGD680", 100000, LocalDate.of(2023, 8,7),
                LocalDate.of(2023, 8,7), "PETROL", 10.0, "MANUAL"));
        CarAddDto carToAdd5=new CarAddDto();
        carToAdd1.setBrand("Mercedes");
        carToAdd1.setModel("W210");
        carToAdd5.setCarDetails(new CarDetailsAddDto("KNAFE121655150144", "red", 2014, 125.0, "limousine",
                5, 5, "8GUS219", 100000, LocalDate.of(2022, 5,11),
                LocalDate.of(2022, 5,11), "DIESEL", 8.0, "AUTOMATIC"));
        CarAddDto carToAdd6=new CarAddDto();
        carToAdd1.setBrand("BMW");
        carToAdd1.setModel("E90");
        carToAdd6.setCarDetails(new CarDetailsAddDto("1HGCS22828A015872", "grey", 2017, 140.0, "coupe",
                2, 2, "BB8716", 100000, LocalDate.of(2022, 1,4),
                LocalDate.of(2022, 1,4), "PETROL", 9.0, "MANUAL"));
        CarAddDto carToAdd7=new CarAddDto();
        carToAdd7.setCarDetails(new CarDetailsAddDto("1GKEK13ZX4R133161", "green", 2017, 120.0, "SUV",
                5, 5, "7MIS838", 100000, LocalDate.of(2023, 05,4),
                LocalDate.of(2023, 05,4), "DIESEL", 10.0, "AUTOMATIC"));
        CarAddDto carToAdd8=new CarAddDto();
        carToAdd1.setBrand("Ford");
        carToAdd1.setModel("Galaxy");
        carToAdd8.setCarDetails(new CarDetailsAddDto("5GTDN13E278135999", "white", 2020, 135.0, "VAN",
                5, 5, "688APL", 100000, LocalDate.of(2022, 05,1),
                LocalDate.of(2022, 05,1), "PETROL", 8.0, "MANUAL"));
        CarAddDto carToAdd9=new CarAddDto();
        carToAdd1.setBrand("BMW");
        carToAdd1.setModel("X3");
        carToAdd9.setCarDetails(new CarDetailsAddDto("1N4AL3AP4FC172795", "black", 2016, 120.0, "hatchback",
                2, 5, "8CBV110", 100000, LocalDate.of(2023, 05,2),
                LocalDate.of(2023, 05,2), "DIESEL", 9.0, "MANUAL"));
        //when
        carService.addCar(carToAdd1);
        carService.addCar(carToAdd2);
        carService.addCar(carToAdd3);
        carService.addCar(carToAdd4);
        carService.addCar(carToAdd5);
        carService.addCar(carToAdd6);
        carService.addCar(carToAdd7);
        carService.addCar(carToAdd8);
        carService.addCar(carToAdd9);

    }

    @Test
    void addCarMethodShouldReturnInstanceOfCar(){
        //given
        CarAddDto carToAdd=new CarAddDto();
        //when
        Car returnedCar = carService.addCar(carToAdd);
        //then
        assertInstanceOf(Car.class, returnedCar);
    }

    @Test
    void addCarMethodShouldReturnInstanceOfCarWithNotEmptyCarDetails(){
        //given
        CarAddDto carToAdd=new CarAddDto();
        //when
        Car returnedCar = carService.addCar(carToAdd);
        //then
        assertNotNull(returnedCar.getCarDetails());
    }

    @Test
    void addCarCarShouldAutoGenerateCarId(){
        //given
        CarAddDto carToAdd=new CarAddDto();
        carToAdd.setBrand("BMW");
        carToAdd.setModel("X3");
        //when
        Car car = carService.addCar(carToAdd);
        //then
        assertNotNull(car.getId());
    }

    @Test
    void createCarWithCarAddDtoShouldReturnCarWithTheSameParameters(){
        //given
        CarAddDto carToAdd=new CarAddDto();
        carToAdd.setBrand("BMW");
        carToAdd.setModel("X3");
        CarDetailsAddDto carDetailsToAdd=new CarDetailsAddDto();
        carDetailsToAdd.setColor("red");
        carDetailsToAdd.setFuel("diesel");
        carDetailsToAdd.setPrice(100.0);
        carToAdd.setCarDetails(carDetailsToAdd);
        //when
        Car car = carService.addCar(carToAdd);
        //then
        assertAll(
                ()->assertEquals(carToAdd.getBrand(), car.getBrand()),
                ()->assertEquals(carToAdd.getModel(), car.getModel()),
                ()->assertEquals(carToAdd.isAvailable(), car.isAvailable()),
                ()->assertEquals(carToAdd.isDeleted(), car.isDeleted()),
                ()->assertEquals(carToAdd.getCarDetails().getColor(), car.getCarDetails().getColor()),
                ()->assertEquals(carToAdd.getCarDetails().getFuel(), car.getCarDetails().getFuel()),
                ()->assertEquals(carToAdd.getCarDetails().getPrice(), car.getCarDetails().getPrice()),
                ()->assertNull(car.getCarDetails().getDoors()),
                ()->assertNull(car.getCarDetails().getSegment())
        );
    }

    @Test
    void searchCarByIdWhereCarWithGivenIdExistShouldReturnCarDto(){
        //given
        CarAddDto carDto=new CarAddDto();
        //when
        Car car =carService.addCar(carDto);
        CarDto carDtoFromDb=carService.findById(car.getId());
        //then
        assertNotNull(carDtoFromDb);
    }

    @Test
    void searchCarByIdWhereCarWithGivenIdExistShouldReturnInstanceOfCarDto(){
        //given
        CarAddDto carDto=new CarAddDto();
        //when
        Car car= carService.addCar(carDto);
        //then
        assertInstanceOf(Car.class, car);
    }

    @Test
    void searchCarByIdWhereCarWithGivenIdNotExistShouldReturnNull(){
        //given
        int id=Integer.MAX_VALUE;
        //when
        CarDto car=carService.findById(id);
        //then
        assertNull(car);
    }

    @Test
    void findAllMethodWithEmptyDatabaseShouldReturnEmptyCarPage(){
        //given
        PageRequest request= PageRequest.of(0, 5);
        //when
        tearDown(); //CLEAR DATABASE
        Page<CarDto> emptyCarsPage= carService.getAll(request);
        //then
        assertEquals(0, emptyCarsPage.getTotalElements());
    }

    @Test
    void findAllMethodShouldReturnCarPageWithSomeCars(){
        //given
        PageRequest request= PageRequest.of(0, 5);
        //when
        Page<CarDto> notEmptyCarsPage= carService.getAll(request);
        //then
        assertNotEquals(0, notEmptyCarsPage.getTotalElements());
    }

    @Test
    void updateCarMethodShouldReturnNullIfCarWithGivenIdNotExist(){
        //given
        int id=Integer.MAX_VALUE;
        CarUpdateDto carUpdateDto=new CarUpdateDto();
        //when
        CarDto returnedCar=carService.updateCar(id, carUpdateDto);
        //then
        assertNull(returnedCar);
    }

    @Test
    void updateCarMethodShouldReturnUpdatedCarWithTheSameFieldsWhatsInCarUpdateDto(){
        //given
        CarAddDto seedCar=new CarAddDto(new CarDetailsAddDto());
        seedCar.setBrand("NoBrand");
        seedCar.getCarDetails().setColor("NoColor");
        seedCar.getCarDetails().setRegistrationYear(2000);
        Car rootCar=carService.addCar(seedCar);
        String carBrand=rootCar.getBrand();
        String carColor=rootCar.getCarDetails().getColor();
        int carRegistrationYear=rootCar.getCarDetails().getRegistrationYear();
        CarUpdateDto carUpdateDto=new CarUpdateDto(new CarDetailsUpdateDto());
        carUpdateDto.setBrand("TestBrand");
        carUpdateDto.getCarDetails().setColor("TestColor");
        carUpdateDto.getCarDetails().setRegistrationYear(2010);
        //when
        CarDto returnedCar=carService.updateCar(rootCar.getId(), carUpdateDto);
        //then
        assertAll(
                ()->assertNotEquals(carBrand, returnedCar.getBrand()),
                ()->assertNotEquals(carColor, returnedCar.getCarDetails().getColor()),
                ()->assertNotEquals(carRegistrationYear, returnedCar.getCarDetails().getRegistrationYear())
        );
    }

    @Test
    void updateCarMethodShouldReturnCarWithNoChangeFieldsWhenFieldsInCarUpdateDtoIsNull(){
        //given
        CarAddDto seedCar=new CarAddDto(new CarDetailsAddDto());
        seedCar.setBrand("NoBrand");
        seedCar.getCarDetails().setColor("NoColor");
        seedCar.getCarDetails().setRegistrationYear(2000);
        Car rootCar=carService.addCar(seedCar);
        String carBrand=rootCar.getBrand();
        String carColor=rootCar.getCarDetails().getColor();
        int carRegistrationYear=rootCar.getCarDetails().getRegistrationYear();
        CarUpdateDto carUpdateDto=new CarUpdateDto(new CarDetailsUpdateDto());
        //when
        CarDto returnedCar=carService.updateCar(rootCar.getId(), carUpdateDto);
        //then
        assertAll(
                ()->assertEquals(carBrand, returnedCar.getBrand()),
                ()->assertEquals(carColor, returnedCar.getCarDetails().getColor()),
                ()->assertEquals(carRegistrationYear, returnedCar.getCarDetails().getRegistrationYear())
        );
    }

    @Test
    void deleteCarMethodShouldReturnTrueIfCarWithGivenIdExists(){
        //given
        CarAddDto carToAdd=new CarAddDto();
        carToAdd.setBrand("BMW");
        carToAdd.setModel("X3");
        Car carBeforeDelete=carService.addCar(carToAdd);
        //when
        boolean carWithGivenIdExist =carService.deleteCar(carBeforeDelete.getId());
        //then
        assertTrue(carWithGivenIdExist);
    }

    @Test
    void deleteCarMethodShouldReturnFalseIfCarWithGivenIdNotExists(){
        //given
        int id=Integer.MAX_VALUE;
        //when
        boolean carWithGivenIdExist =carService.deleteCar(id);
        //then
        assertFalse(carWithGivenIdExist);
    }

    @Test
    void deletedCarShouldChangeDeleteFieldToTrue(){
        //given
        CarAddDto carToAdd=new CarAddDto();
        carToAdd.setBrand("BMW");
        carToAdd.setModel("X3");
        Car carBeforeDelete=carService.addCar(carToAdd);
        CarDto carAfterDelete;
        //when
        carService.deleteCar(carBeforeDelete.getId());
        carAfterDelete=carService.findById(carBeforeDelete.getId());
        //then
        assertEquals(true, carAfterDelete.isDeleted());
    }

    @Test
    void deletedCarShouldChangeAvailableFieldToFalse(){
        //given
        CarAddDto carToAdd=new CarAddDto();
        carToAdd.setBrand("BMW");
        carToAdd.setModel("X3");
        Car carBeforeDelete=carService.addCar(carToAdd);
        CarDto carAfterDelete;
        //when
        carService.deleteCar(carBeforeDelete.getId());
        carAfterDelete=carService.findById(carBeforeDelete.getId());
        //then
        assertEquals(false, carAfterDelete.isAvailable());
    }

    @Test
    void searchMethodShouldReturnPageObjectWithSomeCarDtoObjects(){
        //given
        CarSearchDto carSearchDto=new CarSearchDto();
        Pageable pageable=PageRequest.of(0,6);
        //when
        Page page=carService.search(pageable, carSearchDto);
        //then
        assertInstanceOf(CarDto.class, page.getContent().get(0));
    }

    @Test
    void searchMethodWithEmptyCarSearchDtoShouldReturnAllAvailableCarsFromDatabase(){
        //given
        CarSearchDto carSearchDto=new CarSearchDto();
        Pageable pageable=PageRequest.of(0,6);
        int expectingCarQuantity=6;
        //when
        Page page=carService.search(pageable, carSearchDto);
        //then
        assertEquals(6, page.getTotalElements());
    }

    @Test
    void searchMethodWithGivenIdFieldShouldReturnCarWithTheSameIdIfExist(){
        //given
        CarSearchDto carSearchDto=new CarSearchDto();
        int expectingId=1;
        carSearchDto.setId(expectingId);
        Pageable pageable=PageRequest.of(0,6);
        //when
        Page<CarDto> page=carService.search(pageable, carSearchDto);
        //then
        assertAll(
                ()->assertEquals(1,page.getContent().size(),"Check if only one item is found  "),
                ()->assertEquals(expectingId, page.getContent().get(0).getId(), "Check if ID is the same")
        );
    }

    @Test
    void searchMethodWithGivenParametersWhichIsNotEqualToAnyCarInDataBaseShouldReturnEmptyPage(){
        //given
        CarSearchDto carSearchDto=new CarSearchDto();
        carSearchDto.setColor("NotExistingColorInMyDatabase");
        Pageable pageable=PageRequest.of(0,6);
        //when
        Page<CarDto> page=carService.search(pageable, carSearchDto);
        //then
        assertEquals(0, page.getTotalElements());
    }

    @Test
    void searchMethodWithGivenBrandAndModelShouldReturnMatchingCarsDto(){
        //given
        CarSearchDto carSearchDto=new CarSearchDto();
        carSearchDto.setBrand("BMW");
        carSearchDto.setModel("X3");
        int expectingCarQuantity=1;
        Pageable pageable=PageRequest.of(0,6);
        //when
        Page<CarDto> page=carService.search(pageable, carSearchDto);
        //then
        assertAll(
                ()->assertEquals(expectingCarQuantity, page.getTotalElements()),
                ()->assertThat(page.getContent()).extracting("brand").contains("BMW"),
                ()->assertThat(page.getContent()).extracting("model").contains("X3")
        );
    }

    @Test
    void searchMethodWithGivenRentalAndColorShouldReturnMatchingCarsDto(){
        //given
        CarSearchDto carSearchDto=new CarSearchDto();
        carSearchDto.setColor("black");
        carSearchDto.setRental(2);
        int expectingCarQuantity=2;
            Pageable pageable=PageRequest.of(0,6);
        //when
        Page<CarDto> page=carService.search(pageable, carSearchDto);
        //then
        assertAll(
                ()->assertEquals(expectingCarQuantity, page.getTotalElements()),
                ()->assertThat(page.getContent()).extracting("carDetails.color").contains("black"),
                ()->assertThat(page.getContent()).extracting("rental.id").contains(2)
        );
    }

    @Test
    void searchMethodWithGivenMinimalRegistrationYearAndMaximalPriceShouldReturnMatchingCarsDto(){
        //given
        CarSearchDto carSearchDto=new CarSearchDto();
        int year=2018;
        double price=111.0;
        int expectingCarQuantity=1;
        carSearchDto.setRegistrationYear(year);
        carSearchDto.setPrice(price);
        Pageable pageable=PageRequest.of(0,6);
        //when
        Page<CarDto> page=carService.search(pageable, carSearchDto);
        List<Integer> yearList=page.getContent().stream()
                .map(carDto->carDto.getCarDetails().getRegistrationYear()).collect(Collectors.toList());
        List<Double> priceList=page.getContent().stream()
                .map(carDto -> carDto.getCarDetails().getPrice()).collect(Collectors.toList());
        //then
        assertAll(
                ()->assertEquals(expectingCarQuantity, page.getTotalElements()),
                ()->assertTrue(yearList.get(0)>=year),
                ()->assertTrue(priceList.get(0)<=price)
        );
    }

    @Test
    void searchMethodWithGivenSegmentAndDoorsQuantityShouldReturnMatchingCarsDto(){
        //given
        CarSearchDto carSearchDto=new CarSearchDto();
        int doors=5;
        String segment="limousine";
        int expectingCarQuantity=1;
        carSearchDto.setDoors(doors);
        carSearchDto.setSegment(segment);
        Pageable pageable=PageRequest.of(0,6);
        //when
        Page<CarDto> page=carService.search(pageable, carSearchDto);
        List<Integer> doorsList=page.getContent().stream()
                .map(carDto->carDto.getCarDetails().getDoors()).collect(Collectors.toList());
        //then
        assertAll(
                ()->assertEquals(expectingCarQuantity, page.getTotalElements()),
                ()->assertTrue(doorsList.get(0)==doors),
                ()->assertThat(page.getContent()).extracting("carDetails.segment")
                        .contains(segment)
        );
    }

    @Test
    void searchMethodWithGivenSeatsAndFuelShouldReturnMatchingCarsDto(){
        //given
        CarSearchDto carSearchDto=new CarSearchDto();
        int seats=5;
        String fuel="diesel";
        int expectingCarQuantity=3;
        carSearchDto.setSeats(seats);
        carSearchDto.setFuel(fuel);
        Pageable pageable=PageRequest.of(0,6);
        //when
        Page<CarDto> page=carService.search(pageable, carSearchDto);
        List<Integer> seatsList=page.getContent().stream()
                .map(carDto->carDto.getCarDetails().getDoors()).collect(Collectors.toList());
        //then
        assertAll(
                ()->assertEquals(expectingCarQuantity, page.getTotalElements()),
                ()->assertTrue(seatsList.get(0)==seats),
                ()->assertThat(page.getContent()).extracting("carDetails.fuel")
                        .contains(fuel)
        );
    }


}
