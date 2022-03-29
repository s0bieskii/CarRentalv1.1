package com.car.rental.user;

import com.car.rental.user.dto.UserAddDto;
import com.car.rental.user.dto.UserDto;
import com.car.rental.user.dto.UserLoginDto;
import com.car.rental.user.dto.UserUpdateDto;
import javax.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login/login.html";
        }
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") UserLoginDto user, BindingResult bindingResult, ModelMap modelMap){

        if(bindingResult.hasErrors()){
            return "login/login.html";
        }
        return "main/aboutView.html";
    }

    @GetMapping("/rents")
    public String getUserRents(ModelMap modelMap){

        return "main/aboutView.html";
    }

    @GetMapping("/account")
    public String getMyAccountView(ModelMap modelMap){
        modelMap.addAttribute("user", userService.getCurrentLoggedUser());
        return "user/account.html";
    }

    @GetMapping("/account/edit")
    public String getMyAccountEditView(ModelMap modelMap){

        modelMap.addAttribute("userEdit", new UserUpdateDto());
        modelMap.addAttribute("user", userService.getCurrentLoggedUser());
        return "user/account-edit.html";
    }

    @PostMapping("/account/edit")
    public String saveAccountEdit(@ModelAttribute("userEdit") UserUpdateDto userEdit, BindingResult bindingResult, ModelMap modelMap){


        userEdit.setId(userService.getCurrentLoggedUser().getId());
        UserDto saved = userService.updateUserFix(userEdit);
        modelMap.addAttribute("userEdit", userEdit);
        modelMap.addAttribute("user", userService.findById(saved.getId()));
        return "user/account.html";
    }

}
