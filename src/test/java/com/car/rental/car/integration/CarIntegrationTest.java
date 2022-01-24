package com.car.rental.car.integration;

import com.car.rental.car.Car;
import com.car.rental.car.CarService;
import com.car.rental.car.dto.CarAddDto;
import com.car.rental.car.dto.CarDto;
import com.car.rental.details.dto.CarDetailsAddDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class CarIntegrationTest {
    @Autowired
    private CarService carService;

    @Test
    void createMethodShouldReturnInstanceOfCar(){
        //given
        CarAddDto carToAdd=new CarAddDto();
        //when
        Car returnedCar = carService.addCar(carToAdd);
        //then
        assertInstanceOf(Car.class, returnedCar);
    }

    @Test
    void createCarShouldAutoGenerateCarId(){
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
        int id=5;
        //when
        CarDto car=carService.findById(5);
        //then
        assertNotNull(car);
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


}
