package com.car.rental.car;

import com.car.rental.car.dto.CarAddDto;
import com.car.rental.car.dto.CarDto;
import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.car.dto.CarUpdateDto;
import com.car.rental.car.mapper.CarDtoMapper;
import com.car.rental.car.mapper.CarToAddMapper;
import com.car.rental.car.repository.CarRepository;
import com.car.rental.details.dto.CarDetailsAddDto;
import com.car.rental.utils.EntityUpdater;
import com.car.rental.utils.PageWrapper;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CarService {
    public static final Logger LOGGER = Logger.getLogger(CarService.class.getName());
    private final CarRepository carRepository;
    private final CarToAddMapper carToAddMapper;
    private final CarDtoMapper carMapper;
    private final EntityUpdater entityUpdater;

    public CarService(CarRepository carRepository, CarToAddMapper carToAddMapper, CarDtoMapper carMapper, EntityUpdater entityUpdater){
        LOGGER.info("Creating CarService with "+CarRepository.class+"\n"+ CarToAddMapper.class+"\n"+ CarDtoMapper.class
                +"\n"+ EntityUpdater.class);
        this.carRepository=carRepository;
        this.carToAddMapper=carToAddMapper;
        this.carMapper=carMapper;
        this.entityUpdater=entityUpdater;
    }

    public Car addCar(CarAddDto dto) {
        LOGGER.info("Given body to addCar method: "+dto);
        if(dto.getCarDetails()==null){
            LOGGER.info("Auto create CarDetails to added car");
            dto.setCarDetails(new CarDetailsAddDto());
        }
        Car carToSave=carToAddMapper.toCar(dto);
        LOGGER.info("Car after mapping "+carToSave);
        Car savedCar=carRepository.save(carToSave);
        return savedCar;
    }

    public Page<CarDto> getAll(Pageable pageable){
        Page<Car> cars=carRepository.findAll(pageable);
        LOGGER.info("Find all cars : "+cars);
        Page<CarDto> carsDto=cars.map(carMapper::carToCarDto);
        LOGGER.info("Car after mapping "+carsDto);
        return carsDto;
    }

    public CarDto findById(int id){
        LOGGER.info("Find car by id : id="+id);
        CarDto car=carRepository.findById(id).map(carMapper::carToCarDto).orElse(null);
        LOGGER.info("Car after mapping "+car);
        return car;
    }

    public Page<CarDto> search(Pageable pageable, CarSearchDto carSearchDto){
        LOGGER.info("Try find cars with given parameters: "+carSearchDto);
        List<CarDto> result=carRepository.find(carSearchDto).stream().map(carMapper::carToCarDto).collect(Collectors.toList());
            return PageWrapper.listToPage(pageable, result);
    }

    public CarDto updateCar(int id, CarUpdateDto carDto){
        CarDto dto;
        LOGGER.info("Update car with id="+id+" to: "+carDto);
        Car car;
        if(carRepository.existsById(id)){
            car=carRepository.findById(id).get();
            LOGGER.info("Car with id="+id+" exist");
            try {
                car=(Car)entityUpdater.updateEntity(Hibernate.unproxy(car), carDto);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            carRepository.save(car);
            dto=carMapper.carToCarDto(car);
        } else {
            LOGGER.info("Car with id="+id+" not exist");
            return null;
        }
        LOGGER.info("Car after update: "+dto);
        return dto;
    }

    public boolean deleteCar(int carIdToDelete){
        LOGGER.info("Trying to delete car with id="+carIdToDelete);
        if(carRepository.existsById(carIdToDelete)){
            Car car=carRepository.findById(carIdToDelete).get();
            car.setAvailable(false);
            car.setDeleted(true);
            carRepository.save(car);
            LOGGER.info("Successful deleted car with id="+carIdToDelete);
            return true;
        }
        LOGGER.info("Car with given id not exist");
        return false;
    }
}
