package com.airport.passenger_checkin_service.service.implementation;

import com.airport.passenger_checkin_service.domain.entity.FlightCheckInRecord;
import com.airport.passenger_checkin_service.domain.enums.CheckInStatus;
import com.airport.passenger_checkin_service.domain.enums.FlightStatus;
import com.airport.passenger_checkin_service.domain.dto.request.FlightCheckInRequest;
import com.airport.passenger_checkin_service.domain.dto.response.FlightCheckInResponse;
import com.airport.passenger_checkin_service.exception.*;
import com.airport.passenger_checkin_service.domain.event.CheckInEvent;
import com.airport.passenger_checkin_service.domain.entity.Flight;
import com.airport.passenger_checkin_service.kafka.CheckInEventProducer;
import com.airport.passenger_checkin_service.mapper.CheckInMapper;
import com.airport.passenger_checkin_service.repository.CheckInRepository;
import com.airport.passenger_checkin_service.repository.FlightRepository;
import com.airport.passenger_checkin_service.service.FlightCheckInService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightCheckInServiceImpl implements FlightCheckInService {
    private final CheckInRepository checkInRepository;
    private final CheckInMapper checkInMapper;
    private final FlightRepository flightRepository;
    private final CheckInEventProducer eventProducer;

    @Override
    public FlightCheckInResponse createCheckIn(FlightCheckInRequest request) {
        validateFlight(request.getFlightNumber());
        validatePassengerNotAlreadyCheckedIn(request);
        validateSeatsAvailability(request);

        FlightCheckInRecord checkIn = checkInMapper.toEntity(request);
        FlightCheckInRecord saved = checkInRepository.save(checkIn);

        String boardingPassUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/boarding-pass/")
                .path(saved.getId().toHexString())
                .toUriString();
        saved.setBoardingPassUrl(boardingPassUrl);

        FlightCheckInRecord updated = checkInRepository.save(saved);

        eventProducer.sendCheckInEvent(CheckInEvent.builder()
                .eventType(CheckInStatus.CHECKEDIN)
                .timestamp(Instant.now())
                .build()
        );

        return checkInMapper.toResponse(updated);
    }

    @Override
    public FlightCheckInResponse getCheckInById(ObjectId checkInId) {
        FlightCheckInRecord flightCheckInRecord = checkInRepository.findById(checkInId)
                .orElseThrow(() -> new FlightCheckInNotFoundException("Flight check-in with ID " + checkInId + " was not found."));
        return checkInMapper.toResponse(flightCheckInRecord);
    }

    @Override
    public List<FlightCheckInResponse> getCheckInsByFlightNumber(String flightNumber) {
        List<FlightCheckInRecord> checkIns = checkInRepository.findByFlightNumber(flightNumber);
        return checkInMapper.toResponses(checkIns);
    }

    @Override
    public FlightCheckInResponse updateSeatAssignment(ObjectId checkInId, String seatNumber) {
        FlightCheckInRecord checkIn = checkInRepository.findById(checkInId)
                .orElseThrow(() -> new FlightCheckInNotFoundException("Flight check-in with ID " + checkInId + " was not found."));
        if (checkIn.getSeatNumbers().size() >= 2 && checkIn.getSeatNumbers().contains(seatNumber)) {
            throw new MaxSeatLimitExceededException("Passenger cannot reserve more than 2 seats");
        }

        checkIn.getSeatNumbers().add(seatNumber);
        checkInRepository.save(checkIn);
        return checkInMapper.toResponse(checkInRepository.save(checkIn));
    }

    @Override
    public FlightCheckInResponse updateBaggageInfo(ObjectId checkInId, int baggageCount) {
        FlightCheckInRecord checkIn = checkInRepository.findById(checkInId)
                .orElseThrow(() -> new FlightCheckInNotFoundException("Check-in not found with id " + checkInId));

        if (baggageCount < 0) {
            throw new InvalidBaggageCountException("Baggage count cannot be negative");
        }

        checkIn.setBaggageCount(baggageCount);
        checkIn.setBaggageChecked(baggageCount > 0);
        return checkInMapper.toResponse(checkInRepository.save(checkIn));
    }

    @Override
    public void cancelCheckIn(ObjectId checkInId) {
        checkInRepository.deleteById(checkInId);
    }

    private void validateFlight(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new FlightNotFoundException("Flight " + flightNumber + " not found"));

        if(flight.getStatus() == FlightStatus.CANCELLED
        || flight.getStatus() == FlightStatus.ARRIVED
        || flight.getStatus() == FlightStatus.DEPARTED){
            throw new FlightUnavailableException("Check-in not allowed for flight: " + flightNumber + "\n See the Status! ");
        }
    }

    private void validatePassengerNotAlreadyCheckedIn(FlightCheckInRequest request) {
        String flightNumber = request.getFlightNumber();
        String passportNumber = request.getPassportNumber();
        if (checkInRepository.existsByFlightNumberAndPassportNumber(flightNumber, passportNumber)) {
            throw new DuplicateCheckInException(flightNumber, passportNumber);
        }
    }

    private void validateSeatsAvailability(FlightCheckInRequest request) {
        if (request.getSeatNumbers() != null && !request.getSeatNumbers().isEmpty()) {
            String flightNumber = request.getFlightNumber();
            List<String> takenSeats = request.getSeatNumbers().stream()
                    .filter(seat -> checkInRepository.existsByFlightNumberAndSeatNumbersContains(flightNumber, seat))
                    .toList();

            if (!takenSeats.isEmpty()) {
                throw new SeatAlreadyTakenException(flightNumber, takenSeats);
            }
        }
    }

}