package com.car.rental.details;

import com.car.rental.car.CarController;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class CarDetailsService {
    public static final Logger LOGGER = Logger.getLogger(CarController.class.getName());
    private CarDetailsAddDtoMapper carDetailsAddDtoMapper;
    private CarDetailsRepository carDetailsRepository;

    private CarDetailsService(CarDetailsRepository carDetailsRepository, CarDetailsAddDtoMapper carDetailsMapper){
        this.carDetailsAddDtoMapper=carDetailsMapper;
        this.carDetailsRepository=carDetailsRepository;
    }

    public CarDetails create(CarDetailsAddDto carDetailsDto){
        LOGGER.info("Create carAddDto "+carDetailsDto);
        CarDetails carDetails=carDetailsAddDtoMapper.toCarDetails(carDetailsDto);
        LOGGER.info("Map carDetailsAddDto to CarDetails "+carDetails);
        return carDetails;
    }
}
