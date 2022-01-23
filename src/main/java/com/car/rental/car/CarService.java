package com.car.rental.car;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CarService {
    public static final Logger LOGGER = Logger.getLogger(CarService.class.getName());
    private final CarRepository carRepository;
    private final CarToAddMapper carMapper;


    public CarService(CarRepository carRepository, CarToAddMapper carMapper){
        this.carRepository=carRepository;
        this.carMapper=carMapper;
    }

    public Car addCar(CarAddDto dto) {
        LOGGER.info("Create carAddDto "+dto);
        Car car=carMapper.toCar(dto);
        LOGGER.info("Map carAddDto to Car "+car);
        carRepository.save(car);
        return car;
    }
}
