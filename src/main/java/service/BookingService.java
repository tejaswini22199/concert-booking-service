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
        List<Seat> selectedSeats = seatRepository.findAllById(booking.getSeats().stream().map(Seat::getId).toList());
        
        if (selectedSeats.stream().anyMatch(seat -> !seat.isAvailable())) {
            throw new RuntimeException("One or more seats are already booked");
        }

        selectedSeats.forEach(seat -> seat.setAvailable(false));
        seatRepository.saveAll(selectedSeats);

        return bookingRepository.save(booking);
    }
}
