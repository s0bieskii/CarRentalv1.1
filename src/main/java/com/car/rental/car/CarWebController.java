package com.car.rental.car;

import com.car.rental.car.dto.CarAddDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/cars")
public class CarWebController {

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
}
