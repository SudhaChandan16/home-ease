package com.broomies.controller;

import com.broomies.service.BookingService;
import com.broomies.service.ProviderService;
import com.broomies.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService; // Would need findAll methods
    private final ProviderService providerService;

    public AdminController(UserService userService, ProviderService providerService) {
        this.userService = userService;
        this.providerService = providerService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // In a real app, I'd implement userService.findAll() and
        // bookingService.findAll()
        model.addAttribute("providers", providerService.getAllProviders());
        return "dashboard/admin";
    }
}
