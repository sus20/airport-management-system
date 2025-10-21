package com.airport.passenger_checkin_service.controller;

import com.airport.passenger_checkin_service.domain.dto.request.FlightCheckInRequest;
import com.airport.passenger_checkin_service.domain.dto.request.UpdateSeatRequest;
import com.airport.passenger_checkin_service.domain.dto.response.FlightCheckInResponse;
import com.airport.passenger_checkin_service.service.FlightCheckInService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkins")
@AllArgsConstructor
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

    @GetMapping("/flight/{flightNumber}")
    public ResponseEntity<List<FlightCheckInResponse>> getCheckInsByFlight(@PathVariable String flightNumber) {
        return ResponseEntity.ok(checkInService.getCheckInsByFlightNumber(flightNumber));
    }

    @PatchMapping("/{checkInId}/seats")
    public ResponseEntity<FlightCheckInResponse> updateSeatNumbers(
            @PathVariable ObjectId checkInId,
            @RequestBody @Valid UpdateSeatRequest request) {

        FlightCheckInResponse updated = checkInService.updateSeatNumbers(checkInId, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{checkInId}")
    public ResponseEntity<Void> cancelCheckIn(@PathVariable ObjectId checkInId) {
        checkInService.cancelCheckIn(checkInId);
        return ResponseEntity.noContent().build();
    }

}