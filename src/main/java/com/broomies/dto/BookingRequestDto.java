package com.broomies.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class BookingRequestDto {
    private Long providerId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime serviceDate;

    private Integer durationHours; // New field for duration

    private String serviceAddress;
    private String comments;

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public LocalDateTime getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDateTime serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Integer getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "BookingRequestDto{" +
                "providerId=" + providerId +
                ", serviceDate=" + serviceDate +
                ", durationHours=" + durationHours +
                ", serviceAddress='" + serviceAddress + '\'' +
                ", comments='" + comments + '\'' +
                '}';
    }
}
