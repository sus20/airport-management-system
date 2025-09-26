package com.airport.passenger_checkin_service.service.implementation;

import com.airport.passenger_checkin_service.domain.FlightCheckInRecord;
import com.airport.passenger_checkin_service.dto.FlightCheckInRequest;
import com.airport.passenger_checkin_service.dto.FlightCheckInResponse;
import com.airport.passenger_checkin_service.exception.*;
import com.airport.passenger_checkin_service.mapper.CheckInMapper;
import com.airport.passenger_checkin_service.repository.CheckInRepository;
import com.airport.passenger_checkin_service.service.FlightCheckInService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightCheckInServiceImpl implements FlightCheckInService {
    private final CheckInRepository checkInRepository;
    private final CheckInMapper checkInMapper;

    @Override
    public FlightCheckInResponse createCheckIn(FlightCheckInRequest request) {
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
        return checkInMapper.toResponse(updated);
    }

    @Override
    public FlightCheckInResponse getCheckInById(ObjectId checkInId) {
        FlightCheckInRecord flightCheckInRecord = checkInRepository.findById(checkInId)
                .orElseThrow(() -> new FlightCheckInNotFoundException("Flight check-in with ID " + checkInId + " was not found."));
        return checkInMapper.toResponse(flightCheckInRecord);
    }

    @Override
    public List<FlightCheckInResponse> getCheckInsByFlightId(ObjectId flightId) {
        List<FlightCheckInRecord> checkIns = checkInRepository.findByFlightId(flightId);
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
        if (!checkInRepository.existsById(checkInId)) {
            throw new FlightCheckInNotFoundException("Flight check-in with ID " + checkInId + " was not found.");
        }
        checkInRepository.deleteById(checkInId);
    }

    private void validatePassengerNotAlreadyCheckedIn(FlightCheckInRequest request) {
        ObjectId flightId = new ObjectId(request.getFlightId());
        String passportNumber = request.getPassportNumber();
        if (checkInRepository.existsByFlightIdAndPassportNumber(flightId, passportNumber)) {
            throw new DuplicateCheckInException(flightId.toHexString(), passportNumber);
        }
    }

    private void validateSeatsAvailability(FlightCheckInRequest request) {
        if (request.getSeatNumbers() != null && !request.getSeatNumbers().isEmpty()) {
            ObjectId flightId = new ObjectId(request.getFlightId());
            List<String> takenSeats = request.getSeatNumbers().stream()
                    .filter(seat -> checkInRepository.existsByFlightIdAndSeatNumbersContains(flightId, seat))
                    .toList();

            if (!takenSeats.isEmpty()) {
                throw new SeatAlreadyTakenException(flightId.toHexString(), takenSeats);
            }
        }
    }

}