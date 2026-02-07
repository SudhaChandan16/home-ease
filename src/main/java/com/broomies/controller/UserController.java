package com.broomies.controller;

import com.broomies.dto.BookingRequestDto;
import com.broomies.entity.User;
import com.broomies.service.BookingService;
import com.broomies.service.CustomUserDetails;
import com.broomies.service.ProviderService;
import com.broomies.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    private final BookingService bookingService;
    private final ProviderService providerService;
    private final UserService userService;

    public UserController(BookingService bookingService, ProviderService providerService, UserService userService) {
        this.bookingService = bookingService;
        this.providerService = providerService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User user = userService.findByEmail(userDetails.getUsername());
        model.addAttribute("bookings", bookingService.getBookingsForUser(user));
        return "dashboard/user";
    }

    @GetMapping("/search")
    public String searchProviders(@RequestParam(required = false) String keyword, Model model) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("providers", providerService.searchProviders(keyword));
            model.addAttribute("keyword", keyword);
        } else {
            model.addAttribute("providers", providerService.getAllProviders());
        }
        return "booking/search";
    }

    @GetMapping("/book")
    public String showBookingForm(@RequestParam(required = false) Long providerId, Model model) {
        BookingRequestDto dto = new BookingRequestDto();
        if (providerId != null) {
            dto.setProviderId(providerId);
        }
        model.addAttribute("booking", dto);
        model.addAttribute("providers", providerService.getAllProviders());
        model.addAttribute("categories", com.broomies.enums.ProviderCategory.values());
        return "booking/form";
    }

    @PostMapping("/book")
    public String createBooking(@AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @ModelAttribute("booking") BookingRequestDto dto) {
        User user = userService.findByEmail(userDetails.getUsername());
        bookingService.createBooking(user, dto);
        return "redirect:/user/dashboard?booked=true";
    }

    @GetMapping("/find")
    public String listProviders(@RequestParam(required = false) com.broomies.enums.ProviderCategory category,
            @RequestParam(required = false) String city,
            Model model) {
        java.util.List<com.broomies.entity.Provider> providers;
        if (category != null || city != null) {
            providers = providerService.filterProviders(category, city);
        } else {
            providers = providerService.getAllProviders();
        }

        model.addAttribute("providers", providers);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedCity", city);
        model.addAttribute("categories", com.broomies.enums.ProviderCategory.values());
        return "providers/list";
    }

    @GetMapping("/profile/{id}")
    public String viewProfile(@PathVariable Long id, Model model) {
        try {
            com.broomies.entity.Provider provider = providerService.getProviderById(id);
            model.addAttribute("provider", provider);
            return "providers/profile";
        } catch (RuntimeException e) {
            return "redirect:/user/find";
        }
    }
}
