package com.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bookingservice.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> { }
