package com.broomies.repository;

import com.broomies.entity.Booking;
import com.broomies.entity.Provider;
import com.broomies.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.provider.id = :providerId AND " +
            "b.status = 'CONFIRMED' AND " +
            "((:start < b.endTime AND :end > b.serviceDate))")
    List<Booking> findConflictingBookings(@Param("providerId") Long providerId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end);

    List<Booking> findByUser(User user);

    List<Booking> findByProvider(Provider provider);
}
