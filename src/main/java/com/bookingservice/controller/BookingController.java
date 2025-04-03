package com.bookingservice.controller;

import com.bookingservice.model.Booking;
import com.bookingservice.service.BookingService;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public Booking bookSeats(@RequestBody Booking booking, HttpServletRequest request) {
        // Extract auth token from the request header
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authToken == null || !authToken.startsWith("Bearer ")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authorization token is missing or invalid");
        }

        // Pass the token to BookingService
        return bookingService.bookSeats(booking, authToken);
    }
}


