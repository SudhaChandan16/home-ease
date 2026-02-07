package com.broomies.entity;

import com.broomies.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne
    @JoinColumn(name = "provider_id", nullable = false)
    @ToString.Exclude
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
}
