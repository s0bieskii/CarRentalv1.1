package com.car.rental.car.integration;

import com.car.rental.car.Car;
import com.car.rental.car.CarService;
import com.car.rental.car.dto.CarAddDto;
import com.car.rental.car.dto.CarDto;
import com.car.rental.car.dto.CarUpdateDto;
import com.car.rental.details.dto.CarDetailsAddDto;
import com.car.rental.details.dto.CarDetailsUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.time.LocalDate;

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

    @BeforeEach
    void tearDown(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "car", "car_details");
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
        Page<CarDto> emptyCarsPage= carService.getAll(request);
        //then
        assertEquals(0, emptyCarsPage.getTotalElements());
    }

    @Test
    void findAllMethodShouldReturnCarPageWithSomeCars(){
        //given
        prepareDatabase();
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

}
