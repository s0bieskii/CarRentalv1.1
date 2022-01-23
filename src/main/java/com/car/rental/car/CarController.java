package com.car.rental.car;

import com.car.rental.utils.Config;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.logging.Logger;

@Controller
@RequestMapping("/cars")
public class CarController {
    public static final Logger LOGGER = Logger.getLogger(CarController.class.getName());
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService=carService;
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody CarAddDto dto){
        LOGGER.info("Post mapping request creating "+dto);
        Car returnedCar=carService.addCar(dto);
        LOGGER.info("Car after mapping "+returnedCar);
        return ResponseEntity.created(URI.create(Config.applicationPath+returnedCar.getId())).build();
    }
}
