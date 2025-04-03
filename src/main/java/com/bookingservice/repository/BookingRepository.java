package com.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bookingservice.model.Booking;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> { 
	
    @Query("SELECT s.id FROM Booking b JOIN b.seats s WHERE b.id = :bookingId")
    List<Long> findSeatIdsByBookingId(Long bookingId);

}
