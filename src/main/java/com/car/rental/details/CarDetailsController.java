package com.car.rental.details;

import com.car.rental.details.dto.CarDetailsAddDto;
import com.car.rental.utils.Config;
import java.net.URI;
import java.util.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/car-details")
public class CarDetailsController {
    public static final Logger LOGGER = Logger.getLogger(CarDetailsController.class.getName());
    private final CarDetailsService carDetailsService;

    public CarDetailsController(CarDetailsService carDetailsService) {
        this.carDetailsService = carDetailsService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CarDetailsAddDto carDetailsDto) {
        LOGGER.info("PostMapping requestBody :" + carDetailsDto);
        CarDetails carDetails = carDetailsService.create(carDetailsDto);
        LOGGER.info("Car after mapping " + carDetails);
        return ResponseEntity.created(URI.create(Config.APPLICATION_PATH + carDetails)).build();
    }
}
