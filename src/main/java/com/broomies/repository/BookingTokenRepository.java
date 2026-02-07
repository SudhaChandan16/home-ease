package com.broomies.repository;

import com.broomies.entity.BookingToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingTokenRepository extends JpaRepository<BookingToken, Long> {
    Optional<BookingToken> findByToken(String token);
}
