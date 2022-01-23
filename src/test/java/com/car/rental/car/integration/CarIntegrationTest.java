package com.car.rental.car.integration;

import com.car.rental.car.Car;
import com.car.rental.car.CarAddDto;
import com.car.rental.car.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        //when
        Car car = carService.addCar(carToAdd);
        //then
        assertAll(
                ()->assertEquals(carToAdd.getBrand(), car.getBrand()),
                ()->assertEquals(carToAdd.getModel(), car.getModel()),
                ()->assertEquals(carToAdd.isAvailable(), car.isAvailable()),
                ()->assertEquals(carToAdd.isDeleted(), car.isDeleted())
        );
    }


}
