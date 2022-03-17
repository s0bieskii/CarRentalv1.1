package com.car.rental.main;

import com.car.rental.car.CarService;
import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.details.CarDetailsService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class MainWebController {

    final private CarService carService;
    final private CarDetailsService carDetailsService;

    public MainWebController(CarService carService, CarDetailsService carDetailsService) {
        this.carDetailsService = carDetailsService;
        this.carService=carService;
    }

    @GetMapping()
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
