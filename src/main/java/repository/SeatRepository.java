package com.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bookingservice.model.Seat;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByConcertId(Long concertId);
}
