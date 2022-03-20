package com.car.rental.details;

import com.car.rental.details.dto.CarDetailsAddDto;
import com.car.rental.details.mapper.CarDetailsMapper;
import com.car.rental.details.repository.CarDetailsRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CarDetailsService {
    public static final Logger LOGGER = Logger.getLogger(CarDetailsService.class.getName());
    private final CarDetailsMapper carDetailsAddDtoMapper;
    private final CarDetailsRepository carDetailsRepository;

    private CarDetailsService(CarDetailsRepository carDetailsRepository, CarDetailsMapper carDetailsMapper) {
        LOGGER.info("Creating CarDetailsService with " + CarDetailsRepository.class.getName() + ", " +
                CarDetailsMapper.class.getName());
        this.carDetailsAddDtoMapper = carDetailsMapper;
        this.carDetailsRepository = carDetailsRepository;
    }

    public CarDetails create(CarDetailsAddDto carDetailsDto) {
        LOGGER.info("Given parameter to method: " + carDetailsDto);
        CarDetails carDetails = carDetailsAddDtoMapper.carAddDetailsDtoToCarDetails(carDetailsDto);
        LOGGER.info("CarDetails after mapping :" + carDetails);
        return carDetails;
    }

    public List<String> getCarsColors(){
        LOGGER.info("getCarsColors()");
        return carDetailsRepository.findAllCarDetailsColor().stream().distinct().collect(Collectors.toList());
    }

    public List<String> getCarsFuels(){
        LOGGER.info("getCarsFuels()");
        return carDetailsRepository.findAllCarsFuels();
    }

    public List<String> getCarsSeats(){
        LOGGER.info("getCarsSeats()");
        return carDetailsRepository.findCarsSeats();
    }

    public List<String> getCarsSegment(){
        LOGGER.info("getCarsSegment()");
        return carDetailsRepository.findAllCarsSegments();
    }

    public List<String> getDoorsQuantity(){
        LOGGER.info("getDoorsQuantity()");
        return carDetailsRepository.getDoorsQuantity();
    }

    public BigDecimal getMaxPrice(){
        LOGGER.info("getDoorsQuantity()");
        return carDetailsRepository.getMaxPrice();
    }
}
