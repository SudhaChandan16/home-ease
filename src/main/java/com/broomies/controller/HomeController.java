package com.broomies.controller;

import com.broomies.dto.UserRegistrationDto;
import com.broomies.enums.ProviderCategory;
import com.broomies.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "auth/register";
    }

    @GetMapping("/register/provider")
    public String showProviderRegisterForm(Model model) {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setProvider(true);
        model.addAttribute("user", dto);
        model.addAttribute("categories", ProviderCategory.values());
        return "auth/register-provider";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto dto,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        try {
            userService.registerUser(dto);
            return "redirect:/login?success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }

    @PostMapping("/register/provider")
    public String registerProvider(@Valid @ModelAttribute("user") UserRegistrationDto dto,
            BindingResult result,
            Model model) {
        dto.setProvider(true); // Ensure it's provider
        if (result.hasErrors()) {
            model.addAttribute("categories", ProviderCategory.values());
            return "auth/register-provider";
        }

        try {
            userService.registerUser(dto);
            return "redirect:/login?success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("categories", ProviderCategory.values());
            return "auth/register-provider";
        }
    }
}
