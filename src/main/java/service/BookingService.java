package com.bookingservice.service;

import com.bookingservice.model.Booking;
import com.bookingservice.model.Seat;
import com.bookingservice.repository.BookingRepository;
import com.bookingservice.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;

    public BookingService(BookingRepository bookingRepository, SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
    }

    public Booking bookSeats(Booking booking) {
        booking.setPaymentStatus("PENDING"); // Automatically set to "PENDING"

        // Fetch the seats from DB using their IDs
        List<Seat> selectedSeats = seatRepository.findAllById(
            booking.getSeats().stream().map(Seat::getId).toList()
        );

        // Check if any seat is already booked
        if (selectedSeats.stream().anyMatch(seat -> !seat.isAvailable())) {
            throw new RuntimeException("One or more seats are already booked");
        }

        // Mark seats as unavailable
        selectedSeats.forEach(seat -> seat.setAvailable(false));

        // Save updated seats
        seatRepository.saveAll(selectedSeats);

        // 🔥 Update booking with the correct seat references before saving
        booking.setSeats(selectedSeats);

        // Save the booking
        return bookingRepository.save(booking);
    }
}
