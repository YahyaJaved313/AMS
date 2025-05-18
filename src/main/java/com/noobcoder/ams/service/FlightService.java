package com.noobcoder.ams.service;

import com.noobcoder.ams.model.Flight;
import com.noobcoder.ams.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    private FlightRepository flightRepository;

    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Optional<Flight> getFlightByNumber(String flightNumber) {
        return flightRepository.findById(flightNumber);
    }

    public Flight updateFlight(String flightNumber, Flight updatedFlight) {
        updatedFlight.setFlightNumber(flightNumber);
        return flightRepository.save(updatedFlight);
    }

    public void deleteFlight(String flightNumber) {
        flightRepository.deleteById(flightNumber);
    }
}