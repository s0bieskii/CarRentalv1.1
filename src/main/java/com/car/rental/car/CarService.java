package com.car.rental.car;

import com.car.rental.car.dto.CarAddDto;
import com.car.rental.car.dto.CarDto;
import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.car.dto.CarUpdateDto;
import com.car.rental.car.mapper.CarMapper;
import com.car.rental.car.repository.CarRepository;
import com.car.rental.details.dto.CarDetailsAddDto;
import com.car.rental.rental.repository.RentalRepository;
import com.car.rental.utils.PageWrapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CarService {
    public static final Logger LOGGER = Logger.getLogger(CarService.class.getName());
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final RentalRepository rentalRepository;

    public CarService(CarRepository carRepository, CarMapper carMapper, RentalRepository rentalRepository) {
        LOGGER.info("Creating CarService with(" + carRepository + ", " + carMapper + ", " + rentalRepository);
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.rentalRepository = rentalRepository;
    }

    public Car addCar(CarAddDto dto) {
        LOGGER.info("addCar(" + dto + ")");
        if (dto.getCarDetails() == null) {
            LOGGER.info("Auto-create CarDetails to added car");
            dto.setCarDetails(new CarDetailsAddDto());
        }
        Car carToSave = carMapper.carAddToCar(dto);
        if(dto.getCarDetails().getRentalId()!=null){
            rentalRepository.getById(dto.getCarDetails().getRentalId()).getCars().add(carToSave);
        }

        Car savedCar = carRepository.save(carToSave);
        return savedCar;
    }

    public Car addCarWithImage(CarAddDto dto, MultipartFile file) {
        LOGGER.info("addCar(" + dto + ")");
        if (dto.getCarDetails() == null) {
            LOGGER.info("Auto-create CarDetails to added car");
            dto.setCarDetails(new CarDetailsAddDto());
        }
        Car carToSave = carMapper.carAddToCar(dto);
        if(dto.getCarDetails().getRentalId()!=null){
            rentalRepository.getById(dto.getCarDetails().getRentalId()).getCars().add(carToSave);
        }

        Car savedCar = carRepository.save(carToSave);

        if(!file.isEmpty()){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                Path path = Paths.get("/static/images/cars/" + savedCar.getId());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

    public List<String> getCarsBrands(){
        LOGGER.info("getCarsBrands()");
        return carRepository.getAllCarsBrands();
    }

    public List<String> getCarsModels(){
        LOGGER.info("getCarsModels()");
        return carRepository.getAllCarsModels();
    }

    public CarSearchDto checkSearchStartAndEndDate(CarSearchDto carSearch){
        LOGGER.info("checkSearchStartAndEndDate("+carSearch.getStart()+", "+carSearch.getEnd());
        if(carSearch.getStart()!=null && carSearch.getEnd()!=null){
            if(carSearch.getStart().isAfter(carSearch.getEnd())){
                carSearch.setEnd(carSearch.getStart().plusDays(1));
            }
            if(!carSearch.getStart().isBefore(carSearch.getEnd())){
                carSearch.setEnd(carSearch.getStart().plusDays(1));
            }
        }
        return carSearch;
    }



}
