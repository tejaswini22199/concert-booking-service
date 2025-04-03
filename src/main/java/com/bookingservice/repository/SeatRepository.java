package com.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.bookingservice.model.Seat;

import jakarta.persistence.LockModeType;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    
    List<Seat> findByConcertId(Long concertId);

    // Fetch seats with PESSIMISTIC_WRITE lock to prevent race conditions
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Seat s WHERE s.id IN :seatIds")
    List<Seat> findSeatsForUpdate(@Param("seatIds") List<Long> seatIds);
}