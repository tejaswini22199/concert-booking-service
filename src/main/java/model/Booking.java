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
    
    private String paymentStatus = "PENDING"; // Default to PENDING
    
    // Getters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
