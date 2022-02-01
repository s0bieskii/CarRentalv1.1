package com.car.rental.car;

import com.car.rental.car.dto.CarAddDto;
import com.car.rental.car.dto.CarDto;
import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.car.dto.CarUpdateDto;
import com.car.rental.utils.Config;
import java.net.URI;
import java.util.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cars")
public class CarController {
    public static final Logger LOGGER = Logger.getLogger(CarController.class.getName());
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody CarAddDto dto) {
        LOGGER.info("PostMapping create(" + dto + ")");
        Car returnedCar = carService.addCar(dto);

        return ResponseEntity.created(URI.create(Config.applicationPath + returnedCar.getId())).build();
    }

    @GetMapping()
    public ResponseEntity<Page<CarDto>> getAll(@PageableDefault(size = 6, page = 0) Pageable pageable) {
        LOGGER.info("GetMapping create(" + pageable + ")");
        Page<CarDto> carsDto = carService.getAll(pageable);
        LOGGER.info("Found: " + carsDto);
        return ResponseEntity.ok(carsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> findById(@PathVariable Long id) {
        LOGGER.info("GetMapping findById(" + id + ")");
        CarDto carDto = carService.findById(id);
        return ResponseEntity.ok(carDto);
    }

    @GetMapping("/search")
    public ResponseEntity search(@PageableDefault(size = 6, page = 0) Pageable pageable,
                                 @RequestBody CarSearchDto carSearchDto) {
        LOGGER.info("GetMapping search(" + pageable + ", " + carSearchDto + ")");
        return ResponseEntity.ok(carService.search(pageable, carSearchDto));
    }

    @PatchMapping
    public ResponseEntity updateCarByReflection(@RequestBody CarUpdateDto updateCar) throws IllegalAccessException {
        LOGGER.info("PatchMapping updateCarFields(" + updateCar + ")");
        if (carService.updateCarByReflection(updateCar) != null) {
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Update failed");
        return ResponseEntity.badRequest().body("Something gone wrong with update!");
    }

    @PatchMapping
    public ResponseEntity updateCar(@RequestBody CarUpdateDto updateCar) {
        LOGGER.info("PatchMapping updateCar(" + updateCar + ")");
        if (carService.updateCar(updateCar) != null) {
            return ResponseEntity.noContent().build();
        }
        LOGGER.info("Update failed");
        return ResponseEntity.badRequest().body("Something gone wrong with update!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCar(@PathVariable Long id) {
        LOGGER.info("DeleteMapping deleteCar(" + id + ")");
        if (carService.deleteCar(id)) {
            return ResponseEntity.ok().body("Car with " + id + " successfully deleted");
        }
        return ResponseEntity.badRequest().body("Car with given ID not exist");
    }
}
