package com.bookingservice.controller;

import com.bookingservice.model.Booking;
import com.bookingservice.model.Payment;
import com.bookingservice.service.BookingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public Booking bookSeats(@RequestBody Booking booking) {
        return bookingService.bookSeats(booking);
    }
}
