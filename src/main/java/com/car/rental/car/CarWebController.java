package com.car.rental.car;

import com.car.rental.car.dto.CarAddDto;
import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.details.CarDetailsService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/cars")
public class CarWebController {

    final private CarService carService;
    final private CarDetailsService carDetailsService;

    public CarWebController(CarService carService, CarDetailsService carDetailsService) {
        this.carDetailsService = carDetailsService;
        this.carService=carService;
    }

    @GetMapping("/create")
    public String createView(ModelMap modelMap){
        CarAddDto dto = new CarAddDto();
        dto.setBrand("Ford");
        dto.setModel("Galaxy");
        modelMap.addAttribute("car", dto);
        return "cars/create-car.html";
    }

    @PostMapping()
    public String create(){
        return "cars/cars.html";
    }

    @GetMapping("/search")
    public String searchCarCreateView(ModelMap modelMap){
        modelMap.addAttribute("searchCar", new CarSearchDto());
        modelMap.addAttribute("colors", carDetailsService.getCarsColors());
        modelMap.addAttribute("fuels", carDetailsService.getCarsFuels());
        modelMap.addAttribute("seats", carDetailsService.getCarsSeats());
        modelMap.addAttribute("segments", carDetailsService.getCarsSegment());
        modelMap.addAttribute("doors", carDetailsService.getDoorsQuantity());
        modelMap.addAttribute("cars", carService.getAll(Pageable.ofSize(9)).getContent());
        return "car-offer/rent-a-car.html";
    }
}
