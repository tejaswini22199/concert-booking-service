package com.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bookingservice.model.SeatLock;
import java.util.Optional;

public interface SeatLockRepository extends JpaRepository<SeatLock, Long> {
    Optional<SeatLock> findBySeatId(Long seatId);
}
