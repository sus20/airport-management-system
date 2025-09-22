package com.airport.passenger_checkin_service.controller;

import com.airport.passenger_checkin_service.dto.PassengerRequest;
import com.airport.passenger_checkin_service.dto.PassengerResponse;
import com.airport.passenger_checkin_service.service.PassengerService;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/passengers")
public class PassengerController {
    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerResponse> createPassenger(@RequestBody PassengerRequest passengerRequest) {
        return ResponseEntity.ok(passengerService.createPassenger(passengerRequest));
    }

    @GetMapping
    public ResponseEntity<List<PassengerResponse>> getPassengers() {
        return ResponseEntity.ok(passengerService.getAllPassengers());
    }
}