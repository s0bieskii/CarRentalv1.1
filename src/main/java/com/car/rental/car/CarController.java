package com.car.rental.car;

import com.car.rental.car.dto.CarAddDto;
import com.car.rental.car.dto.CarDto;
import com.car.rental.car.dto.CarUpdateDto;
import com.car.rental.utils.Config;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        LOGGER.info("PostMapping requestBody :"+dto);
        Car returnedCar=carService.addCar(dto);
        LOGGER.info("Car after mapping "+returnedCar);
        return ResponseEntity.created(URI.create(Config.applicationPath+returnedCar.getId())).build();
    }

    @GetMapping
    public ResponseEntity<Page<CarDto>> getAll(@RequestParam Pageable pageable){
        LOGGER.info("GetMapping getAll(Pageable pageable)");
        Page<CarDto> carsDto=carService.getAll(pageable);
        return ResponseEntity.ok(carsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> findById(@PathVariable int id){
        LOGGER.info("GetMapping findBy(id) : id="+id);
        CarDto carDto=carService.findById(id);
        return ResponseEntity.ok(carDto);
    }

    //TODO zaimpelmentuj
    @GetMapping("/search")
    public ResponseEntity<Page<CarDto>> search(){
        return null;
    }


    @PatchMapping("/{id}")
    public ResponseEntity updateCar(@PathVariable int id, @RequestBody CarUpdateDto carDto) throws IllegalAccessException {
        CarDto carAfterUpdate=carService.updateCar(id, carDto);
        if(carAfterUpdate==null){
            return ResponseEntity.badRequest().body("Car with this ID not exists");
        }
        return ResponseEntity.ok("Car with "+id+" successfully updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCar(@PathVariable int id){
        return null;
    }
}
