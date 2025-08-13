package com.airport.flightscheduler.controller;

import com.airport.flightscheduler.domain.Flight;
import com.airport.flightscheduler.dto.FlightDTO;
import com.airport.flightscheduler.enumeration.FlightStatus;
import com.airport.flightscheduler.service.FlightService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(("/flights"))
@AllArgsConstructor
@Slf4j
public class FlightController {
    private final FlightService flightService;

    @GetMapping
    public ResponseEntity<List<FlightDTO>> getFlights(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(flightService.getAllFlights(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlightById(@PathVariable ObjectId id) {
        log.debug("Get flight by id: {}", id);
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PostMapping
    public ResponseEntity<FlightDTO> createFlight(@Valid @RequestBody Flight flight) {
        log.info(" POST /flights creating flight {}", flight.getFlightNumber());
        FlightDTO savedFlight = flightService.createFlight(flight);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{}")
                .buildAndExpand(savedFlight.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedFlight);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightDTO> updateFlight(@PathVariable ObjectId id, @Valid @RequestBody Flight flight) {
        return ResponseEntity.ok(flightService.updateFlight(id, flight));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable ObjectId id) {
        log.info(" DELETE /flights/ {}", id);
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<FlightStatus> getFlightStatus(@PathVariable ObjectId id) {
        log.debug("GET /flights/{}/status", id);
        return ResponseEntity.ok().body(flightService.getFlightById(id).getStatus());
    }
}