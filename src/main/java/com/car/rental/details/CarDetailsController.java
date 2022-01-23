package com.car.rental.details;

import com.car.rental.car.CarController;
import com.car.rental.utils.Config;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.util.logging.Logger;

@Controller
@RequestMapping("/car-details")
public class CarDetailsController{
    public static final Logger LOGGER = Logger.getLogger(CarController.class.getName());
    private final CarDetailsService carDetailsService;

    public CarDetailsController(CarDetailsService carDetailsService){
        this.carDetailsService=carDetailsService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CarDetailsAddDto carDetailsDto){
        LOGGER.info("Creating CarDetailsAddDto: "+carDetailsDto);
        CarDetails carDetails=carDetailsService.create(carDetailsDto);
        return ResponseEntity.created(URI.create(Config.applicationPath+carDetails)).build();
    }
}
