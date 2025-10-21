package com.airport.passenger_checkin_service.controller;

import com.airport.passenger_checkin_service.domain.dto.request.BaggageRequest;
import com.airport.passenger_checkin_service.domain.dto.request.BaggageStatusUpdateRequest;
import com.airport.passenger_checkin_service.domain.dto.response.BaggageResponse;
import com.airport.passenger_checkin_service.service.BaggageService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/baggage")
@RequiredArgsConstructor
public class BaggageController {

    private final BaggageService baggageService;

    @GetMapping("/{baggageId}")
    public ResponseEntity<BaggageResponse> getBaggageById(@PathVariable ObjectId baggageId) {
        BaggageResponse baggage = baggageService.getBaggageById(baggageId);
        return ResponseEntity.ok(baggage);
    }

    @PatchMapping("/{baggageId}/weight")
    public ResponseEntity<BaggageResponse> updateBaggageWeight(
            @PathVariable ObjectId baggageId,
            @RequestBody BaggageRequest request
    ) {
        BaggageResponse updated = baggageService.updateWeight(baggageId, request.getWeight());
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{baggageId}/status")
    public ResponseEntity<BaggageResponse> updateBaggageStatus(
            @PathVariable ObjectId baggageId,
            @RequestBody BaggageStatusUpdateRequest request
    ) {
        BaggageResponse updated = baggageService.updateStatus(baggageId, request.getStatus());
        return ResponseEntity.ok(updated);
    }

}