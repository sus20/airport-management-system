package com.airport.passenger_checkin_service.controller;

import com.airport.passenger_checkin_service.dto.FlightCheckInRequest;
import com.airport.passenger_checkin_service.dto.FlightCheckInResponse;
import com.airport.passenger_checkin_service.service.FlightCheckInService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkins")
@RequiredArgsConstructor
public class FlightCheckInController {

    private final FlightCheckInService checkInService;

    @PostMapping
    public ResponseEntity<FlightCheckInResponse> createCheckIn(@Valid @RequestBody FlightCheckInRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(checkInService.createCheckIn(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightCheckInResponse> getCheckInById(@PathVariable ObjectId id) {
        return ResponseEntity.ok(checkInService.getCheckInById(id));
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<FlightCheckInResponse>> getCheckInsByFlight(@PathVariable ObjectId flightId) {
        return ResponseEntity.ok(checkInService.getCheckInsByFlightId(flightId));
    }

    @PutMapping("/{checkInId}/seats")
    public ResponseEntity<FlightCheckInResponse> updateSeatAssignment(
            @PathVariable ObjectId checkInId,
            @RequestParam String seatNumber) {
        return ResponseEntity.ok(checkInService.updateSeatAssignment(checkInId, seatNumber));
    }

    @PutMapping("/{checkInId}/baggage")
    public ResponseEntity<FlightCheckInResponse> updateBaggageInfo(
            @PathVariable ObjectId checkInId,
            @RequestParam int baggageCount) {
        return ResponseEntity.ok(checkInService.updateBaggageInfo(checkInId, baggageCount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelCheckIn(@PathVariable ObjectId id) {
        checkInService.cancelCheckIn(id);
        return ResponseEntity.noContent().build();
    }
}