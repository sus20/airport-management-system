package com.airport.flightscheduler.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> index() {
        log.info("GET / - Home endpoint accessed");
        return ResponseEntity.ok(
                Map.of(
                        "message", "Welcome! Please use /flights or visit /swagger-ui.html for API documentation."
                )
        );
    }
}