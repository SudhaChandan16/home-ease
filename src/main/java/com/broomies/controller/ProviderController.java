package com.broomies.controller;

import com.broomies.entity.Provider;
import com.broomies.entity.User;
import com.broomies.service.BookingService;
import com.broomies.service.CustomUserDetails;
import com.broomies.service.ProviderService;
import com.broomies.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/provider")
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
    public ResponseEntity<?> dashboard(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());
        if (user.getProviderProfile() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "User is not a provider"));
        }

        Provider provider = user.getProviderProfile();
        return ResponseEntity.ok(Map.of(
            "provider", provider,
            "bookings", bookingService.getBookingsForProvider(provider)
        ));
    }

    @PostMapping("/availability")
    public ResponseEntity<?> updateAvailability(@AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(name = "isAvailable", defaultValue = "false") boolean isAvailable) {
        User user = userService.findByEmail(userDetails.getUsername());
        providerService.updateAvailability(Objects.requireNonNull(user.getProviderProfile().getId()), isAvailable);
        return ResponseEntity.ok(Map.of("message", "Availability updated successfully"));
    }
}
