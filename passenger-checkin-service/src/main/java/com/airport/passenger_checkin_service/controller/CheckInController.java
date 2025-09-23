package com.airport.passenger_checkin_service.controller;

import com.airport.passenger_checkin_service.dto.CheckInRequest;
import com.airport.passenger_checkin_service.dto.CheckInResponse;
import com.airport.passenger_checkin_service.service.CheckInService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkins")
@RequiredArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    @PostMapping
    public ResponseEntity<CheckInResponse> createCheckIn(@Valid @RequestBody CheckInRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(checkInService.checkIn(request));
    }
}