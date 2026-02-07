package com.broomies.entity;

import com.broomies.enums.BookingStatus;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    private LocalDateTime serviceDate; // Start Time
    private LocalDateTime endTime; // End Time

    private Double totalAmount; // Calculated Cost

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private String serviceAddress;

    private String comments;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Booking() {
    }

    public Booking(Long id, User user, Provider provider, LocalDateTime serviceDate, LocalDateTime endTime,
            Double totalAmount, BookingStatus status, String serviceAddress, String comments, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.provider = provider;
        this.serviceDate = serviceDate;
        this.endTime = endTime;
        this.totalAmount = totalAmount;
        this.status = status;
        this.serviceAddress = serviceAddress;
        this.comments = comments;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public LocalDateTime getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDateTime serviceDate) {
        this.serviceDate = serviceDate;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", serviceDate=" + serviceDate +
                ", endTime=" + endTime +
                ", totalAmount=" + totalAmount +
                ", status=" + status +
                ", serviceAddress='" + serviceAddress + '\'' +
                ", comments='" + comments + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
