package com.broomies.controller;

import com.broomies.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/booking/response")
    public String handleBookingResponse(@RequestParam String token,
            @RequestParam String action,
            Model model) {
        String result = bookingService.processBookingResponse(token, action);
        model.addAttribute("message", result);
        return "booking/response";
    }
}
