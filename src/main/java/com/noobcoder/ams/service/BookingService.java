package com.noobcoder.ams.service;

import com.noobcoder.ams.model.Booking;
import com.noobcoder.ams.model.Flight;
import com.noobcoder.ams.repository.BookingRepository;
import com.noobcoder.ams.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Transactional
    public Booking bookFlight(Long userId, String flightNumber, int numberOfTickets) {
        Optional<Flight> flightOpt = flightRepository.findById(flightNumber);
        if (flightOpt.isEmpty()) {
            throw new RuntimeException("Flight not found");
        }
        Flight flight = flightOpt.get();
        if (flight.getAvailableSeats() < numberOfTickets) {
            throw new RuntimeException("Not enough seats available");
        }
        flight.setAvailableSeats(flight.getAvailableSeats() - numberOfTickets);
        flightRepository.save(flight);

        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setFlightNumber(flightNumber);
        booking.setNumberOfTickets(numberOfTickets);
        booking.setBookingDate(LocalDateTime.now());
        booking.setStatus("CONFIRMED");
        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Transactional
    public void cancelBooking(Long bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new RuntimeException("Booking not found");
        }
        Booking booking = bookingOpt.get();
        Optional<Flight> flightOpt = flightRepository.findById(booking.getFlightNumber());
        if (flightOpt.isEmpty()) {
            throw new RuntimeException("Flight not found");
        }
        Flight flight = flightOpt.get();
        flight.setAvailableSeats(flight.getAvailableSeats() + booking.getNumberOfTickets());
        flightRepository.save(flight);

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }
}