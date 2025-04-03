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
    
    // Getters
    public Long getId() {
        return id;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getSeatType() {
        return seatType;
    }

    public Concert getConcert() {
        return concert;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }
}
