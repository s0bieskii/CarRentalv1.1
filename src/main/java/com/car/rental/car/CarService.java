package com.car.rental.car;

import com.car.rental.car.dto.CarAddDto;
import com.car.rental.car.dto.CarDto;
import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.car.dto.CarUpdateDto;
import com.car.rental.car.mapper.CarMapper;
import com.car.rental.car.repository.CarRepository;
import com.car.rental.details.dto.CarDetailsAddDto;
import com.car.rental.rental.repository.RentalRepository;
import com.car.rental.utils.EntityUpdater;
import com.car.rental.utils.PageWrapper;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    public static final Logger LOGGER = Logger.getLogger(CarService.class.getName());
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final RentalRepository rentalRepository;
    private final EntityUpdater entityUpdater;

    public CarService(CarRepository carRepository, CarMapper carMapper, RentalRepository rentalRepository,
                      EntityUpdater entityUpdater) {
        LOGGER.info("Creating CarService with(" + carRepository + ", " + carMapper + ", " + rentalRepository + ", " +
                entityUpdater);
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.rentalRepository = rentalRepository;
        this.entityUpdater = entityUpdater;
    }

    public Car addCar(CarAddDto dto) {
        LOGGER.info("addCar(" + dto + ")");
        if (dto.getCarDetails() == null) {
            LOGGER.info("Auto-create CarDetails to added car");
            dto.setCarDetails(new CarDetailsAddDto());
        }
        Car carToSave = carMapper.carAddToCar(dto);
        LOGGER.info("Car to save after mapping " + carToSave);
        Car savedCar = carRepository.save(carToSave);
        return savedCar;
    }

    public Page<CarDto> getAll(Pageable pageable) {
        LOGGER.info("getAll(" + pageable);
        Page<CarDto> carsDto = carRepository.findAll(pageable).map(carMapper::carToCarDto);
        LOGGER.info("All founded cars: " + carsDto);
        return carsDto;
    }

    public CarDto findById(Long id) {
        LOGGER.info("findById(" + id + ")");
        CarDto car = carRepository.findById(id).map(carMapper::carToCarDto).orElse(null);
        LOGGER.info("Found: " + car);
        return car;
    }

    public Page<CarDto> search(Pageable pageable, CarSearchDto carSearchDto) {
        LOGGER.info("search(" + pageable + ", " + carSearchDto + ")");
        List<CarDto> carResultList = carRepository.find(carSearchDto).stream()
                .map(carMapper::carToCarDto).collect(Collectors.toList());
        LOGGER.info("Found: " + carResultList);
        return PageWrapper.listToPage(pageable, carResultList);
    }

    public CarDto updateCarByReflection(CarUpdateDto carUpdate) throws IllegalAccessException {
        LOGGER.info("updateCarByReflection(" + carUpdate + ")");
        if (!carRepository.existsById(carUpdate.getId())) {
            LOGGER.info("Car with given ID not exist" + carUpdate.getId());
            return null;
        }
        LOGGER.info("Car with given ID exist");
        Car carToUpdate = (Car) Hibernate.unproxy(carRepository.getById(carUpdate.getId()));
        Car carAfterUpdate = (Car) entityUpdater.updateEntity(carToUpdate, carUpdate);
        carRepository.save(carAfterUpdate);
        CarDto carDto = carMapper.carToCarDto(carAfterUpdate);
        LOGGER.info("Update success! Car after update: " + carDto);
        return carDto;
    }

    public CarDto updateCar(CarUpdateDto carUpdate) {
        LOGGER.info("updateCar(" + carUpdate + ")");
        if (carUpdate.getId() != null && carRepository.existsById(carUpdate.getId())) {
            Car car = carMapper.carUpdateToCar(carUpdate);
            Car afterSave = carRepository.save(car);
            LOGGER.info("Car successfully updated");
            return carMapper.carToCarDto(afterSave);
        }
        return null;
    }


    public boolean deleteCar(Long carIdToDelete) {
        LOGGER.info("deleteCar(" + carIdToDelete + ")");
        if (carRepository.existsById(carIdToDelete)) {
            LOGGER.info("Car with given id exist");
            Car car = carRepository.findById(carIdToDelete).get();
            car.setAvailable(false);
            car.setDeleted(true);
            carRepository.save(car);
            LOGGER.info("Successful deleted car with id=" + carIdToDelete);
            return true;
        }
        LOGGER.info("Car with given id not exist");
        return false;
    }
}
