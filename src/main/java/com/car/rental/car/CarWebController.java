package com.car.rental.car;

import com.car.rental.car.dto.CarAddDto;
import com.car.rental.car.dto.CarDto;
import com.car.rental.car.dto.CarSearchDto;
import com.car.rental.details.CarDetailsService;
import com.car.rental.details.dto.CarDetailsAddDto;
import com.car.rental.rent.RentService;
import com.car.rental.rent.dto.RentAddDto;
import com.car.rental.rental.RentalService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    final private RentService rentService;

    public CarWebController(CarService carService, CarDetailsService carDetailsService, RentalService rentalService,
                            RentService rentService) {
        this.carDetailsService = carDetailsService;
        this.carService = carService;
        this.rentalService = rentalService;
        this.rentService = rentService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
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
        carSearchDto.setStart(LocalDateTime.now());
        carSearchDto.setEnd(LocalDateTime.now().plusDays(1));

        modelMap.addAttribute("cars", carsPage);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("searchCar", carSearchDto);
        modelMap.addAttribute("rentals", rentalService.findAll());
        modelMap.addAttribute("colors", carDetailsService.getCarsColors());
        modelMap.addAttribute("fuels", carDetailsService.getCarsFuels());
        modelMap.addAttribute("seats", carDetailsService.getCarsSeats());
        modelMap.addAttribute("segments", carDetailsService.getCarsSegment());
        modelMap.addAttribute("doors", carDetailsService.getDoorsQuantity());
        modelMap.addAttribute("models", carService.getCarsModels());
        modelMap.addAttribute("brands", carService.getCarsBrands());
        modelMap.addAttribute("maxPrice", carDetailsService.getMaxPrice());
        return "cars/rent-a-car.html";
    }

    @PostMapping("/search")
    public String searchCarCreateView(@RequestParam(required = true, value = "page",
            defaultValue = "1") Integer page,
                                      @RequestParam(required = true, value = "size",
                                              defaultValue = "3") Integer size,
                                      @Valid @ModelAttribute("searchCar") CarSearchDto carSearch,
                                      BindingResult bindingResult,
                                      ModelMap modelMap) {
        Page<CarDto> carsPage = carService.search(PageRequest.of(page - 1, size), carSearch);


        int totalPages = carsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelMap.addAttribute("pageNumbers", pageNumbers);
        }
        carSearch = carService.checkSearchStartAndEndDate(carSearch);

        modelMap.addAttribute("cars", carsPage);
        modelMap.addAttribute("currentPage", page);
        modelMap.addAttribute("searchCar", carSearch);
        modelMap.addAttribute("rentals", rentalService.findAll());
        modelMap.addAttribute("colors", carDetailsService.getCarsColors());
        modelMap.addAttribute("fuels", carDetailsService.getCarsFuels());
        modelMap.addAttribute("seats", carDetailsService.getCarsSeats());
        modelMap.addAttribute("segments", carDetailsService.getCarsSegment());
        modelMap.addAttribute("doors", carDetailsService.getDoorsQuantity());
        modelMap.addAttribute("models", carService.getCarsModels());
        modelMap.addAttribute("brands", carService.getCarsBrands());
        modelMap.addAttribute("maxPrice", carDetailsService.getMaxPrice());
        return "cars/rent-a-car.html";
    }

    @GetMapping("/offer/{id}")
    public String getSingleCarOfferView(@PathVariable Long id, ModelMap modelMap) {
        CarSearchDto searchCar = new CarSearchDto();
        searchCar.setStart(LocalDateTime.now());
        searchCar.setEnd(LocalDateTime.now().plusDays(1));
        searchCar.setId(id);
        modelMap.addAttribute("id", id);
        modelMap.addAttribute("car", carService.findById(id));
        modelMap.addAttribute("searchCar", searchCar);
        modelMap.addAttribute("rental", rentalService.getRentalByCarId(id));
        modelMap.addAttribute("price", null);
        return "cars/car-offer.html";
    }

    @PostMapping("/offer/{id}")
    public String getSingleCarOfferView(@PathVariable Long id,
                                        @Valid @ModelAttribute("searchCar") CarSearchDto carSearch,
                                        BindingResult bindingResult,
                                        ModelMap modelMap) {
        carSearch.setId(id);
        carSearch = carService.checkSearchStartAndEndDate(carSearch);
        modelMap.addAttribute("id", id);
        modelMap.addAttribute("car", carService.findById(id));
        modelMap.addAttribute("searchCar", carSearch);
        modelMap.addAttribute("rental", rentalService.getRentalByCarId(id));
        modelMap.addAttribute("price",
                rentService.calculateRentFinalPrice(carSearch.getId(), carSearch.getStart(), carSearch.getEnd()));
        return "cars/car-offer.html";
    }

    @PostMapping("/offer/success/{id}")
    public String confirmOffer(@PathVariable Long id,
                               @Valid @ModelAttribute("searchCar") CarSearchDto carSearch,
                               BindingResult bindingResult,
                               ModelMap modelMap) {

        carSearch.setId(id);
        carSearch = carService.checkSearchStartAndEndDate(carSearch);
        modelMap.addAttribute("id", id);
        modelMap.addAttribute("car", carService.findById(id));
        modelMap.addAttribute("searchCar", carSearch);
        modelMap.addAttribute("rental", rentalService.getRentalByCarId(id));
        modelMap.addAttribute("price",
                rentService.calculateRentFinalPrice(carSearch.getId(), carSearch.getStart(), carSearch.getEnd()));
        if (bindingResult.hasErrors()) {
            return "cars/car-offer.html";
        }
        RentAddDto rent = new RentAddDto();
        rent.setCarId(id);
        rent.setUserId(2L);
        rent.setStart(carSearch.getStart());
        rent.setEnd(carSearch.getEnd());
        rent.setComment("Nice try");
        rentService.addRent(rent);
        return "cars/rent-success.html";
    }

    @GetMapping("/add")
    public String getAddCarView(ModelMap modelMap) {
        CarAddDto carAddDto = new CarAddDto();
        carAddDto.setCarDetails(new CarDetailsAddDto());
        modelMap.addAttribute("addCar", carAddDto);
        modelMap.addAttribute("rentals", rentalService.findAll());
        return "cars/add-car.html";
    }

    @PostMapping("/add")
    public String addNewCar(@Valid @ModelAttribute("addCar") CarAddDto carAddDto, BindingResult bindingResult,
                            ModelMap modelMap) {
        modelMap.addAttribute("addCar", carAddDto);
        modelMap.addAttribute("rentals", rentalService.findAll());
        if(bindingResult.hasErrors()){
            return "cars/add-car.html";
        }
        carService.addCar(carAddDto);
        return "main/aboutView.html";
    }

}
