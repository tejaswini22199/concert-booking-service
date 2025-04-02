package com.bookingservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private int numberOfSeats;
    
    @OneToMany
    private List<Seat> seats;
    
    private String paymentStatus; // PENDING, SUCCESS, FAILED
}
