package com.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bookingservice.model.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> { }
