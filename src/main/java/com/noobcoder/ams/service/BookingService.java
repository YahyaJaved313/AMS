package com.noobcoder.ams.service;

import com.noobcoder.ams.model.Booking;
import com.noobcoder.ams.model.Flight;
import com.noobcoder.ams.model.User;
import com.noobcoder.ams.repository.BookingRepository;
import com.noobcoder.ams.repository.FlightRepository;
import com.noobcoder.ams.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, FlightRepository flightRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Booking bookFlight(Long userId, String flightNumber, int numberOfTickets) {
        // Validate user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));

        // Validate flight exists and has enough seats
        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new EntityNotFoundException("Flight " + flightNumber + " not found"));
        if (flight.getAvailableSeats() < numberOfTickets) {
            throw new IllegalArgumentException("Not enough available seats");
        }

        // Update flight availability
        flight.setAvailableSeats(flight.getAvailableSeats() - numberOfTickets);
        flightRepository.save(flight);

        // Create and save booking
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setFlightNumber(flightNumber);
        booking.setNumberOfTickets(numberOfTickets);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("CONFIRMED"); // Default status

        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}