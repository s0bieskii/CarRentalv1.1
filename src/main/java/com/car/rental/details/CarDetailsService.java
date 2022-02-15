package com.car.rental.details;

import com.car.rental.car.CarController;
import com.car.rental.details.dto.CarDetailsAddDto;
import com.car.rental.details.mapper.CarDetailsAddDtoMapper;
import com.car.rental.details.repository.CarDetailsRepository;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class CarDetailsService {
    public static final Logger LOGGER = Logger.getLogger(CarController.class.getName());
    private CarDetailsAddDtoMapper carDetailsAddDtoMapper;
    private CarDetailsRepository carDetailsRepository;

    private CarDetailsService(CarDetailsRepository carDetailsRepository, CarDetailsAddDtoMapper carDetailsMapper) {
        LOGGER.info("Creating CarService with " + CarDetailsRepository.class.getName() + ", " +
                CarDetailsAddDtoMapper.class.getName());
        this.carDetailsAddDtoMapper = carDetailsMapper;
        this.carDetailsRepository = carDetailsRepository;
    }

    public CarDetails create(CarDetailsAddDto carDetailsDto) {
        LOGGER.info("Given parameter to method: " + carDetailsDto);
        CarDetails carDetails = carDetailsAddDtoMapper.toCarDetails(carDetailsDto);
        LOGGER.info("CarDetails after mapping :" + carDetails);
        return carDetails;
    }
}
