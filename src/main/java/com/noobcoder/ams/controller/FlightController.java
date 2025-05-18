package com.noobcoder.ams.controller;

import com.noobcoder.ams.model.Flight;
import com.noobcoder.ams.service.FlightService;
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
        Flight savedFlight = flightService.addFlight(flight);
        return ResponseEntity.ok(savedFlight);
    }

    @GetMapping("/admin/flights")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @GetMapping("/admin/flights/{flightNumber}")
    public ResponseEntity<Flight> getFlightByNumber(@PathVariable String flightNumber) {
        Optional<Flight> flight = flightService.getFlightByNumber(flightNumber);
        return flight.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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