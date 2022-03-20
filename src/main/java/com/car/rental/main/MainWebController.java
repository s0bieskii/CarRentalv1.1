package com.car.rental.main;

import com.car.rental.car.CarService;
import com.car.rental.car.dto.CarDto;
import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.details.CarDetailsService;
import com.car.rental.rental.RentalService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping()
public class MainWebController {

    final private CarService carService;
    final private CarDetailsService carDetailsService;
    final private RentalService rentalService;



    public MainWebController(CarService carService, CarDetailsService carDetailsService, RentalService rentalService) {
        this.carDetailsService = carDetailsService;
        this.carService = carService;
        this.rentalService = rentalService;
    }

    @GetMapping()
    public String searchCarCreateView(ModelMap modelMap) {
        return "main/aboutView.html";
    }

    @PostMapping()
    public String searchCarCreateView(ModelMap modelMap,
                                      @RequestParam(required = true, value = "page",
                                              defaultValue = "1") Integer page,
                                      @RequestParam(required = true, value = "size",
                                              defaultValue = "3") Integer size,
                                      CarSearchDto carSearch) {
        Page<CarDto> carsPage = carService.search(PageRequest.of(page - 1, size), carSearch);

        int totalPages = carsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }

        modelMap.addAttribute("cars", carsPage);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("searchCar", carSearch);
        modelMap.addAttribute("colors", carDetailsService.getCarsColors());
        modelMap.addAttribute("fuels", carDetailsService.getCarsFuels());
        modelMap.addAttribute("seats", carDetailsService.getCarsSeats());
        modelMap.addAttribute("segments", carDetailsService.getCarsSegment());
        modelMap.addAttribute("doors", carDetailsService.getDoorsQuantity());
        modelMap.addAttribute("models", carService.getCarsModels());
        modelMap.addAttribute("brands", carService.getCarsBrands());
        modelMap.addAttribute("maxPrice", carDetailsService.getMaxPrice());
        return "car-offer/rent-a-car.html";
    }

    @GetMapping("/contact")
    public String contactView(ModelMap modelMap){
        return "main/contact.html";
    }
}
