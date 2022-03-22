package com.car.rental.rental;

import com.car.rental.car.CarService;
import com.car.rental.details.CarDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/rentals")
public class RentalWebController {

    final private CarService carService;
    final private CarDetailsService carDetailsService;
    final private RentalService rentalService;

    public RentalWebController(CarService carService, CarDetailsService carDetailsService,
                               RentalService rentalService) {
        this.carService = carService;
        this.carDetailsService = carDetailsService;
        this.rentalService = rentalService;
    }

    @GetMapping("/{id}")
    public String getRentalView(@PathVariable Long id, ModelMap modelMap){

        modelMap.addAttribute("rental", rentalService.getRentalByCarId(id));
        return "rental/rental.html";
    }
}
