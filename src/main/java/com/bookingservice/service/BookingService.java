package com.bookingservice.service;

import org.hibernate.boot.jaxb.JaxbLogger.logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bookingservice.model.Booking;
import com.bookingservice.model.Seat;
import com.bookingservice.repository.BookingRepository;
import com.bookingservice.repository.SeatRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
	
	private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
	
    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private final RestTemplate restTemplate;

    @Value("${auth.service.url}")
    private String authServiceUrl; // Load from application.properties

    public BookingService(BookingRepository bookingRepository, SeatRepository seatRepository, RestTemplate restTemplate) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.restTemplate = restTemplate;
    }
    
    private List<Long> getSeatIdsByBookingId(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .map(booking -> booking.getSeats().stream()
                        .map(Seat::getId)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));
    }


    @Transactional
    public Booking bookSeats(Booking booking, String authToken) {
        // Step 1: Validate User with Auth Service
        if (!isUserAuthenticated(authToken)) {
            throw new RuntimeException("User authentication failed! Please login.");
        }

        // Step 2: Fetch Seats with Pessimistic Lock
        List<Seat> selectedSeats = seatRepository.findSeatsForUpdate(booking.getSeatIds());

        // Step 3: Check if Any Seat is Already Booked
        if (selectedSeats.stream().anyMatch(seat -> !seat.isAvailable())) {
        	logger.error("Booking failed: One or more seats are already booked!");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "One or more seats are already booked! Please select different seats.");
        }

        // Step 4: Mark Seats as Unavailable & Save
        selectedSeats.forEach(seat -> seat.setAvailable(false));
        seatRepository.saveAll(selectedSeats);

        // Step 5: Save the Booking
        booking.setPaymentStatus("PENDING");
        return bookingRepository.save(booking);
    }

    private boolean isUserAuthenticated(String token) {
    	System.out.println("Auth Service URL: " + authServiceUrl);

        String url = authServiceUrl + "/api/auth/validate";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token.trim()); // Ensure correct format

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Calling Auth Service URL: " + url);
        System.out.println("With Token: " + token.trim());
        
        
        try {
            ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Boolean.class);
            System.out.println("Response from Auth Service: " + response.getBody());
            return Boolean.TRUE.equals(response.getBody());
        } catch (Exception e) {
        	 System.err.println("Error calling Auth Service: " + e.getMessage());
            throw new RuntimeException("Error validating user authentication: " + e.getMessage());
        }
    }
}
