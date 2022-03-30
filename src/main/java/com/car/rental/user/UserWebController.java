package com.car.rental.user;

import com.car.rental.car.dto.CarDto;
import com.car.rental.rent.RentService;
import com.car.rental.rent.dto.RentDto;
import com.car.rental.rent.dto.RentSearchDto;
import com.car.rental.user.dto.UserAddDto;
import com.car.rental.user.dto.UserDto;
import com.car.rental.user.dto.UserLoginDto;
import com.car.rental.user.dto.UserUpdateDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/users")
public class UserWebController {

    private final UserService userService;
    private final RentService rentService;

    public UserWebController(UserService userService, RentService rentService) {
        this.userService = userService;
        this.rentService = rentService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(ModelMap modelMap) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            modelMap.addAttribute("user", new UserAddDto());
            return "register/register.html";
        }
        return "redirect:/";
    }

    @PostMapping("/register")
    public String create(@Valid @ModelAttribute("user") UserAddDto user, BindingResult bindingResult,
                         ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            return "register/register.html";
        }
        userService.addUser(user);
        return "register/register-success.html";
    }

    @GetMapping("/login")
    public String showLoginForm(ModelMap modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login/login.html";
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") UserLoginDto user, BindingResult bindingResult,
                        ModelMap modelMap) {

        if (bindingResult.hasErrors()) {
            return "login/login.html";
        }
        return "main/aboutView.html";
    }

    @GetMapping("/account")
    public String getMyAccountView(ModelMap modelMap) {
        modelMap.addAttribute("user", userService.getCurrentLoggedUser());
        return "user/account.html";
    }

    @GetMapping("/account/edit")
    public String getMyAccountEditView(ModelMap modelMap) {

        modelMap.addAttribute("userEdit", new UserUpdateDto());
        modelMap.addAttribute("user", userService.getCurrentLoggedUser());
        return "user/account-edit.html";
    }

    @PostMapping("/account/edit")
    public String saveAccountEdit(@ModelAttribute("userEdit") UserUpdateDto userEdit, BindingResult bindingResult,
                                  ModelMap modelMap) {
        userEdit.setId(userService.getCurrentLoggedUser().getId());
        UserDto saved = userService.updateUserFix(userEdit);
        modelMap.addAttribute("userEdit", userEdit);
        modelMap.addAttribute("user", userService.findById(saved.getId()));
        return "user/account.html";
    }

    @GetMapping("/account/rents")
    public String getUserRentsView(@Valid @ModelAttribute("user") RentSearchDto rentSearch,
                                   BindingResult bindingResult, ModelMap modelMap) {
        Long id = userService.getCurrentLoggedUser().getId();
        if(rentSearch == null){
            rentSearch = new RentSearchDto();
        }
        rentSearch.setDeleted(false);
        List<CarDto> cars = rentService.getAllRentsByUserId(id).stream().map(rentDto -> rentDto.getCar()).distinct().collect(Collectors.toList());

        rentSearch.setUserId(id);
        List<RentDto> userRents = rentService.search(rentSearch);

                modelMap.addAttribute("rents", userRents);
                modelMap.addAttribute("rentSearch", rentSearch);
                modelMap.addAttribute("cars", cars);
        return "user/user-rents.html";
    }

    @GetMapping("/account/rent/{id}")
    public String getUserRentsView(@PathVariable Long id, ModelMap modelMap) {
        Long userId = userService.getCurrentLoggedUser().getId();

            if(!rentService.existById(userId)){
            modelMap.addAttribute("status", HttpStatus.NOT_FOUND.value());
            modelMap.addAttribute("message", "Rent not found");
            modelMap.addAttribute("timestamp", LocalDateTime.now());
            return "error.html";
        }
        RentDto rent = rentService.findById(id);
        if(rent.getUser().getId()!=userId){
            modelMap.addAttribute("status", HttpStatus.FORBIDDEN.value());
            modelMap.addAttribute("message", "No access!");
            modelMap.addAttribute("timestamp", LocalDateTime.now());
            return "error.html";
        }
        modelMap.addAttribute("rent", rent);
        return "user/single-rent.html";
    }

    @GetMapping("/account/rent/delete/{id}")
    public String getRentDeleteView(@PathVariable Long id, ModelMap modelMap) {
        Long userId = userService.getCurrentLoggedUser().getId();

        if(!rentService.existById(userId)){
            modelMap.addAttribute("status", HttpStatus.NOT_FOUND.value());
            modelMap.addAttribute("message", "Rent not found");
            modelMap.addAttribute("timestamp", LocalDateTime.now());
            return "error.html";
        }
        RentDto rent = rentService.findById(id);
        if(rent.getUser().getId()!=userId){
            modelMap.addAttribute("status", HttpStatus.FORBIDDEN.value());
            modelMap.addAttribute("message", "No access!");
            modelMap.addAttribute("timestamp", LocalDateTime.now());
            return "error.html";
        }
        modelMap.addAttribute("rent", rent);
        return "user/rent-delete.html";
    }

    @PostMapping("/account/rent/delete/{id}")
    public String deleteRent(@PathVariable Long id, ModelMap modelMap) {
        Long userId = userService.getCurrentLoggedUser().getId();

        if(!rentService.existById(userId)){
            modelMap.addAttribute("status", HttpStatus.NOT_FOUND.value());
            modelMap.addAttribute("message", "Rent not found");
            modelMap.addAttribute("timestamp", LocalDateTime.now());
            return "error.html";
        }
        RentDto rent = rentService.findById(id);
        if(rent.getUser().getId()!=userId){
            modelMap.addAttribute("status", HttpStatus.FORBIDDEN.value());
            modelMap.addAttribute("message", "No access!");
            modelMap.addAttribute("timestamp", LocalDateTime.now());
            return "error.html";
        }
        rentService.deleteRent(id);
        return "redirect:/web/users/account/rents";
    }

}
