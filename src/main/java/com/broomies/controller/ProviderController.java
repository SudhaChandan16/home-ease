package com.broomies.controller;

import com.broomies.entity.Provider;
import com.broomies.entity.User;
import com.broomies.service.BookingService;
import com.broomies.service.CustomUserDetails;
import com.broomies.service.ProviderService;
import com.broomies.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/provider")
public class ProviderController {

    private final BookingService bookingService;
    private final UserService userService;
    private final ProviderService providerService;

    public ProviderController(BookingService bookingService, UserService userService, ProviderService providerService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.providerService = providerService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        if (user.getProviderProfile() == null) {
            return "redirect:/"; // Should not happen due to role check
        }

        Provider provider = user.getProviderProfile();
        model.addAttribute("bookings", bookingService.getBookingsForProvider(provider));
        model.addAttribute("provider", provider);
        return "dashboard/provider";
    }

    @PostMapping("/availability")
    public String updateAvailability(@AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(name = "isAvailable", defaultValue = "false") boolean isAvailable) {
        User user = userService.findByEmail(userDetails.getUsername());
        providerService.updateAvailability(user.getProviderProfile().getId(), isAvailable);
        return "redirect:/provider/dashboard";
    }
}
