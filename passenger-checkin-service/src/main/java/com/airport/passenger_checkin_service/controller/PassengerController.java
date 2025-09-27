package com.airport.passenger_checkin_service.controller;

import com.airport.passenger_checkin_service.domain.dto.request.PassengerRequest;
import com.airport.passenger_checkin_service.domain.dto.response.PassengerResponse;
import com.airport.passenger_checkin_service.domain.dto.request.PassengerSearchRequest;
import com.airport.passenger_checkin_service.service.PassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/passengers")
@Slf4j
public class PassengerController {
    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerResponse> createPassenger(@Valid @RequestBody PassengerRequest passengerRequest) {
        return ResponseEntity.ok(passengerService.createPassenger(passengerRequest));
    }

    @GetMapping
    public ResponseEntity<List<PassengerResponse>> getPassengers() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }

    @GetMapping("/search")
    public ResponseEntity<List<PassengerResponse>> searchPassengers( PassengerSearchRequest passengerRequest) {
        return ResponseEntity.ok(passengerService.search(passengerRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassengerById(@PathVariable ObjectId id) {
        return ResponseEntity.ok(passengerService.getPassengerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerResponse> updatePassenger(
            @PathVariable ObjectId id,
            @Valid @RequestBody PassengerRequest request) {
        return ResponseEntity.ok(passengerService.updatePassenger(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable ObjectId id) {
        log.info(" DELETE /passengers/{}", id);
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}