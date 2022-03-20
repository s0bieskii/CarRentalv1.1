package com.car.rental.car;

import com.car.rental.car.dto.CarAddDto;
import com.car.rental.car.dto.CarDto;
import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.details.CarDetailsService;
import com.car.rental.rental.RentalService;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/web/cars")
public class CarWebController {

    final private CarService carService;
    final private CarDetailsService carDetailsService;
    final private RentalService rentalService;

    public CarWebController(CarService carService, CarDetailsService carDetailsService, RentalService rentalService) {
        this.carDetailsService = carDetailsService;
        this.carService=carService;
        this.rentalService = rentalService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
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
    public String searchCarCreateView(ModelMap modelMap,
                                      @RequestParam(required = true, value = "page",
                                              defaultValue = "1") Integer page,
                                      @RequestParam(required = true, value = "size",
                                              defaultValue = "3") Integer size) {
        CarSearchDto carSearch = new CarSearchDto();
        Page<CarDto> carsPage = carService.search(PageRequest.of(page - 1, size), carSearch);

        int totalPages = carsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        CarSearchDto carSearchDto = new CarSearchDto();
        carSearchDto.setPrice(BigDecimal.ZERO);

        modelMap.addAttribute("cars", carsPage);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("searchCar", carSearchDto);
        modelMap.addAttribute("rentals", rentalService.getAll(PageRequest.of(1, 10)).getContent());
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

    @PostMapping("/search")
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

    @GetMapping("/offer/{id}")
    public String getSingleCarOfferView(@PathVariable Long id){
        return "car-offer/car-offer.html";
    }
}
