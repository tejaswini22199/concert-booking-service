package com.bookingservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seatNumber;
    private boolean isAvailable;
    private String seatType;
    
    @ManyToOne
    @JoinColumn(name = "concert_id")
    private Concert concert;
}
