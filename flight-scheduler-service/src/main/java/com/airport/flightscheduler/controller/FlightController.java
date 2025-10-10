package com.airport.flightscheduler.controller;

import com.airport.flightscheduler.domain.dto.request.*;
import com.airport.flightscheduler.domain.dto.response.FlightResponse;
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
    public ResponseEntity<List<FlightResponse>> getFlights(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return ResponseEntity.ok(flightService.getAllFlights(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable ObjectId id) {
        log.debug("Get flight by id: {}", id);
        return ResponseEntity.ok(flightService.getFlightById(id));
    }

    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(@Valid @RequestBody FlightRequest request) {
        log.info(" POST /flights creating flight {}", request.getFlightNumber());
        FlightResponse savedFlight = flightService.createFlight(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{}")
                .buildAndExpand(savedFlight.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedFlight);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(@PathVariable ObjectId id, @Valid @RequestBody FlightRequest request) {
        return ResponseEntity.ok(flightService.updateFlight(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable ObjectId id) {
        log.info(" DELETE /flights/ {}", id);
        flightService.deleteFlight(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/gate")
    public ResponseEntity<FlightResponse> updateGate(
            @PathVariable ObjectId id,
            @Valid @RequestBody FlightUpdateGateRequest request) {
        return ResponseEntity.ok(flightService.updateGate(id, request.getGate()));
    }

    @PatchMapping("/{id}/terminal")
    public ResponseEntity<FlightResponse> updateTerminal(
            @PathVariable ObjectId id,
            @Valid @RequestBody FlightTerminalUpdateRequest request) {
        return ResponseEntity.ok(flightService.updateTerminal(id, request.getTerminal()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<FlightResponse> updateStatus(
            @PathVariable ObjectId id,
            @Valid @RequestBody FlightStatusUpdateRequest request) {
        return ResponseEntity.ok(flightService.updateStatus(id, request.getStatus()));
    }

    @GetMapping("/search")
    public ResponseEntity<List<FlightResponse>> searchFlights(@Valid @ModelAttribute FlightSearchRequest request) {
        return ResponseEntity.ok(flightService.search(request));
    }
}