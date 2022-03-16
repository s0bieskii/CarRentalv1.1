package com.car.rental.user;

import com.car.rental.user.dto.UserAddDto;
import com.car.rental.user.dto.UserLoginDto;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/users")
public class UserWebController {

    private final UserService userService;

    public UserWebController(UserService userService) {
        this.userService=userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(ModelMap modelMap){
        modelMap.addAttribute("user", new UserAddDto());
        return "register/register.html";
    }

    @PostMapping("/register")
    public String create(@Valid @ModelAttribute("user") UserAddDto user, BindingResult bindingResult, ModelMap modelMap){
        if(bindingResult.hasErrors()){
            return "register/register.html";
        }
        userService.addUser(user);
        return "register/register-success.html";
    }

    @GetMapping("/login")
    public String showLoginForm(ModelMap modelMap){
        modelMap.addAttribute("user", new UserLoginDto());
        return "login/login.html";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") UserLoginDto user, BindingResult bindingResult, ModelMap modelMap){
        return "register/register-success.html";
    }
}
