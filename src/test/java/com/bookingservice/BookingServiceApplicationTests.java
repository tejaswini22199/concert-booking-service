package com.bookingservice;

import com.bookingservice.model.Booking;
import com.bookingservice.model.Seat;
import com.bookingservice.model.User;
import com.bookingservice.repository.SeatRepository;
import com.bookingservice.repository.UserRepository;
import com.bookingservice.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookingServiceApplicationTests {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private UserRepository userRepository;

    private Booking testBooking;
    private String validAuthTokenUser1;
    private String validAuthTokenUser2;

    @BeforeEach
    void setUp() {
        // Create test users if they don’t exist
        User user1 = userRepository.findByEmail("user1@example.com")
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail("user1@example.com");
                    newUser.setPassword("password123"); // Store encrypted in real scenario
                    return userRepository.save(newUser);
                });

        User user2 = userRepository.findByEmail("user2@example.com")
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail("user2@example.com");
                    newUser.setPassword("password123");
                    return userRepository.save(newUser);
                });

        validAuthTokenUser1 = "Bearer test-token-user1"; // Simulate authentication tokens
        validAuthTokenUser2 = "Bearer test-token-user2";

        // Fetch an available seat from the DB
        List<Seat> availableSeats = seatRepository.findByIsAvailableTrue();
        assertFalse(availableSeats.isEmpty(), "No available seats found in the DB!");

        Seat seat = availableSeats.get(0); // Pick first available seat

        testBooking = new Booking();
        testBooking.setUserId(user1.getId()); // Assign to user1
        testBooking.setSeats(List.of(seat));
    }

    @Test
    @Transactional
    void testConcurrentSeatBooking() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);
        int[] successCount = {0};

        Runnable task1 = () -> {
            try {
                bookingService.bookSeats(testBooking, validAuthTokenUser1);
                synchronized (successCount) {
                    successCount[0]++;
                }
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " failed: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        };

        Runnable task2 = () -> {
            try {
                bookingService.bookSeats(testBooking, validAuthTokenUser2);
                synchronized (successCount) {
                    successCount[0]++;
                }
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " failed: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        };

        // Start two threads trying to book the same seat
        executor.execute(task1);
        executor.execute(task2);

        latch.await(); // Wait for both tasks to complete
        executor.shutdown();

        // One should succeed, one should fail
        assertEquals(1, successCount[0], "One booking should succeed, the other should fail.");
    }
}
