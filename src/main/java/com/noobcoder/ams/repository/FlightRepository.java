package com.noobcoder.ams.repository;

import com.noobcoder.ams.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, String> {
}