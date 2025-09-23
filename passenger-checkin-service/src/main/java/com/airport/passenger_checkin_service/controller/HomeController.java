package com.airport.passenger_checkin_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> home() {
        log.info("GET ( - Home endpoint accessed");
        return ResponseEntity.ok(Map.of("message", "Welcome! Please visit /swagger-ui/index.html for API documentation.!"));
    }
}