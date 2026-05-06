package com.broomies.controller;

import com.broomies.dto.BookingRequestDto;
import com.broomies.entity.Booking;
import com.broomies.entity.User;
import com.broomies.service.BookingService;
import com.broomies.service.CustomUserDetails;
import com.broomies.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final BookingService bookingService;
    private final UserService userService;

    public UserController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<List<Booking>> dashboard(@AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());
        return ResponseEntity.ok(bookingService.getBookingsForUser(user));
    }

    @PostMapping("/book")
    public ResponseEntity<?> createBooking(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @Valid @RequestBody @NonNull BookingRequestDto dto) {
        User user = userService.findByEmail(userDetails.getUsername());
        bookingService.createBooking(user, dto);
        return ResponseEntity.ok(Map.of("message", "Booking created successfully"));
    }
}
