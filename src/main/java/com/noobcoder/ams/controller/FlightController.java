package com.noobcoder.ams.controller;

import com.noobcoder.ams.model.Flight;
import com.noobcoder.ams.service.FlightService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @PostMapping("/admin/flights")
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
        if (flight.getTotalSeats() < 0 || flight.getAvailableSeats() < 0) {
            throw new IllegalArgumentException("Total seats and available seats must be non-negative");
        }
        if (flight.getAvailableSeats() > flight.getTotalSeats()) {
            throw new IllegalArgumentException("Available seats cannot exceed total seats");
        }
        Flight savedFlight = flightService.addFlight(flight);
        return ResponseEntity.ok(savedFlight);
    }

    @GetMapping("/admin/flights")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/admin/flights/{flightNumber}")
    public ResponseEntity<Flight> getFlightByNumber(@PathVariable String flightNumber) {
        Flight flight = flightService.getFlightByNumber(flightNumber)
                .orElseThrow(() -> new EntityNotFoundException("Flight with number " + flightNumber + " not found"));
        return ResponseEntity.ok(flight);
    }

    @PutMapping("/admin/flights/{flightNumber}")
    public ResponseEntity<Flight> updateFlight(@PathVariable String flightNumber, @RequestBody Flight flight) {
        Flight updatedFlight = flightService.updateFlight(flightNumber, flight);
        return ResponseEntity.ok(updatedFlight);
    }

    @DeleteMapping("/admin/flights/{flightNumber}")
    public ResponseEntity<Void> deleteFlight(@PathVariable String flightNumber) {
        flightService.deleteFlight(flightNumber);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/flights")
    public ResponseEntity<List<Flight>> searchFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }
}