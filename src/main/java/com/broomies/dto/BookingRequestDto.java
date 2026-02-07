package com.broomies.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class BookingRequestDto {
    private Long providerId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime serviceDate;

    private Integer durationHours; // New field for duration

    private String serviceAddress;
    private String comments;
}
