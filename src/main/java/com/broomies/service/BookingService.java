package com.broomies.service;

import com.broomies.dto.BookingRequestDto;
import com.broomies.entity.Booking;
import com.broomies.entity.BookingToken;
import com.broomies.entity.Provider;
import com.broomies.entity.User;
import com.broomies.enums.BookingStatus;
import com.broomies.repository.BookingRepository;
import com.broomies.repository.BookingTokenRepository;
import com.broomies.repository.ProviderRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingTokenRepository tokenRepository;
    private final ProviderRepository providerRepository;
    private final EmailService emailService;

    @Value("${server.port}")
    private String serverPort;

    public BookingService(BookingRepository bookingRepository, BookingTokenRepository tokenRepository,
            ProviderRepository providerRepository, EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.tokenRepository = tokenRepository;
        this.providerRepository = providerRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void createBooking(User user, @NonNull BookingRequestDto dto) {
        Long providerId = dto.getProviderId();
        if (providerId == null) {
            throw new RuntimeException("Provider ID cannot be null");
        }
        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        // Calculate End Time
        int duration = dto.getDurationHours() != null ? dto.getDurationHours() : 1;
        LocalDateTime startTime = dto.getServiceDate();
        LocalDateTime endTime = startTime.plusHours(duration);

        // Check for Conflicts
        List<Booking> conflicts = bookingRepository.findConflictingBookings(provider.getId(), startTime, endTime);
        if (!conflicts.isEmpty()) {
            throw new RuntimeException("Provider is not available at this time.");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setProvider(provider);
        booking.setServiceDate(startTime);
        booking.setEndTime(endTime);

        // Calculate Total Cost
        double rate = provider.getHourlyRate() != null ? provider.getHourlyRate() : 0.0;
        booking.setTotalAmount(rate * duration);

        booking.setServiceAddress(dto.getServiceAddress());
        booking.setComments(dto.getComments());
        booking.setStatus(BookingStatus.PENDING);

        Booking savedBooking = bookingRepository.save(booking);

        // Generate Token
        BookingToken token = new BookingToken(savedBooking);
        tokenRepository.save(token);

        // Send Email to Provider
        sendBookingRequestEmail(provider, savedBooking, token.getToken());
    }

    private void sendBookingRequestEmail(Provider provider, Booking booking, String token) {
        String baseUrl = "http://localhost:" + serverPort + "/booking/response";
        String acceptUrl = baseUrl + "?token=" + token + "&action=ACCEPT";
        String rejectUrl = baseUrl + "?token=" + token + "&action=REJECT";

        Map<String, Object> variables = new HashMap<>();
        variables.put("providerName", provider.getUser().getName());
        variables.put("customerName", booking.getUser().getName());
        variables.put("serviceDate", booking.getServiceDate());
        variables.put("address", booking.getServiceAddress());
        variables.put("acceptUrl", acceptUrl);
        variables.put("rejectUrl", rejectUrl);

        emailService.sendHtmlEmail(Objects.requireNonNull(provider.getUser().getEmail()), "New Booking Request", "email/booking-request",
                variables);
    }

    @Transactional
    public String processBookingResponse(String token, String action) {
        BookingToken bookingToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid Token"));

        if (bookingToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return "Token Expired";
        }

        Booking booking = bookingToken.getBooking();
        if (booking.getStatus() != BookingStatus.PENDING) {
            return "Booking already processed";
        }

        if ("ACCEPT".equalsIgnoreCase(action)) {
            booking.setStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
            sendBookingStatusEmail(booking.getUser(), booking, "Confirmed");
            return "Booking Confirmed";
        } else if ("REJECT".equalsIgnoreCase(action)) {
            booking.setStatus(BookingStatus.REJECTED);
            bookingRepository.save(booking);
            sendBookingStatusEmail(booking.getUser(), booking, "Rejected");
            return "Booking Rejected";
        }

        return "Invalid Action";
    }

    private void sendBookingStatusEmail(User user, Booking booking, String status) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("userName", user.getName());
        variables.put("status", status);
        variables.put("providerName", booking.getProvider().getUser().getName());
        variables.put("serviceDate", booking.getServiceDate());

        emailService.sendHtmlEmail(Objects.requireNonNull(user.getEmail()), "Booking " + status, "email/booking-status", variables);
    }

    public List<Booking> getBookingsForUser(User user) {
        return bookingRepository.findByUser(user);
    }

    public List<Booking> getBookingsForProvider(Provider provider) {
        return bookingRepository.findByProvider(provider);
    }
}
