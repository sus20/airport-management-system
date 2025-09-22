package com.airport.passenger_checkin_service.dto;

import com.airport.passenger_checkin_service.domain.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PassengerRequest {

    @NotBlank(message = "First name required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "Last name required")
    private String lastName;

    @NotNull(message = "Gender required")
    private Gender gender;

    @NotNull(message = "Date of birth required")
    @Past(message = "Date of birth must be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Email(message = "Invalid email address")
    private String email;

    @Pattern(regexp = "^[0-9+\\-]{7,15}$", message = "Invalid phone number")
    private String phoneNumber;

    private String address;

    @NotBlank(message = "Passport number required")
    @Pattern(regexp = "^[A-Za-z0-9]{6,9}$",
            message = "Passport must be 6–9 characters long and contain only letters and numbers")
    private String passportNumber;

    @NotBlank(message = "Passport country required")
    private String passportCountry;

    @Future(message = "Passport expiry must be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate passportExpiryDate;

    @NotBlank(message = "Nationality required")
    private String nationality;
}