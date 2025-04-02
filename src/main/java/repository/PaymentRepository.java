package com.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bookingservice.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByBookingId(Long bookingId);
}
