package com.airport.passenger_checkin_service.service.implementation;

import com.airport.passenger_checkin_service.domain.dto.request.FlightCheckInRequest;
import com.airport.passenger_checkin_service.domain.dto.request.UpdateSeatRequest;
import com.airport.passenger_checkin_service.domain.dto.response.BaggageResponse;
import com.airport.passenger_checkin_service.domain.dto.response.FlightCheckInResponse;
import com.airport.passenger_checkin_service.domain.entity.Flight;
import com.airport.passenger_checkin_service.domain.entity.FlightCheckInRecord;
import com.airport.passenger_checkin_service.domain.enums.BaggageStatus;
import com.airport.passenger_checkin_service.domain.enums.CheckInStatus;
import com.airport.passenger_checkin_service.domain.enums.FlightStatus;
import com.airport.passenger_checkin_service.exception.*;
import com.airport.passenger_checkin_service.mapper.CheckInMapper;
import com.airport.passenger_checkin_service.repository.BaggageRepository;
import com.airport.passenger_checkin_service.repository.CheckInRepository;
import com.airport.passenger_checkin_service.repository.FlightRepository;
import com.airport.passenger_checkin_service.repository.PassengerRepository;
import com.airport.passenger_checkin_service.service.BaggageService;
import com.airport.passenger_checkin_service.service.FlightCheckInService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class FlightCheckInServiceImpl implements FlightCheckInService {
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;
    private final CheckInRepository checkInRepository;
    private final CheckInMapper checkInMapper;
    private final BaggageService baggageService;

    private static final EnumSet<FlightStatus> NON_CHECKIN_ELIGIBLE_STATUSES =
            EnumSet.of(FlightStatus.CANCELLED, FlightStatus.DEPARTED, FlightStatus.ARRIVED);
    private final BaggageRepository baggageRepository;

    @Override
    @Transactional
    public FlightCheckInResponse createCheckIn(FlightCheckInRequest request) {
        Flight flight = validateFlightExists(request.getFlightNumber());
        validatePassengerExists(request.getPassportNumber());
        ensureEligibleForCheckIn(flight);
        ensureSeatAvailability(request.getFlightNumber(), request.getSeatNumbers());

        FlightCheckInRecord checkIn = initializeCheckInRecord(request);
        checkInRepository.save(checkIn);

        processBaggageIfPresent(request, checkIn);

        return buildResponse(checkIn);
    }

    @Override
    public FlightCheckInResponse getCheckInById(ObjectId checkInId) {
        FlightCheckInRecord response = findCheckInRecordOrThrow(checkInId);
        return buildResponse(response);
    }

    @Override
    public List<FlightCheckInResponse> getCheckInsByFlightNumber(String flightNumber) {
        List<FlightCheckInRecord> checkInRecords = checkInRepository.findByFlightNumber(flightNumber);
        return checkInRecords.stream()
                .map(this::buildResponse)
                .collect(Collectors.toList());
    }

    @Override
    public FlightCheckInResponse updateSeatNumbers(ObjectId checkInId, UpdateSeatRequest request) {
        FlightCheckInRecord checkIn = findCheckInRecordOrThrow(checkInId);

        if (checkIn.getStatus() != CheckInStatus.CHECKED_IN) {
            throw new FlightCheckInNotAllowedException("Cannot change seats for cancelled or completed check-ins");
        }

        ensureSeatAvailability(checkIn.getFlightNumber(), request.getSeatNumbers());

        checkIn.setSeatNumbers(request.getSeatNumbers());
        checkInRepository.save(checkIn);

        return buildResponse(checkIn);
    }

    @Override
    public void cancelCheckIn(ObjectId checkInId) {
        FlightCheckInRecord record = findCheckInRecordOrThrow(checkInId);

        record.setStatus(CheckInStatus.CANCELLED);
        checkInRepository.save(record);

        baggageRepository.findByCheckInId(checkInId)
                .forEach( baggageRecord -> {baggageRecord.setStatus(BaggageStatus.OFFLOADED);});
    }

    private Flight validateFlightExists(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber)
                .orElseThrow(() -> new FlightUnavailableException("Flight not found with number: " + flightNumber));
    }

    private void validatePassengerExists(String passportNumber) {
        passengerRepository.findByPassportNumber(passportNumber)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger not found with passport number: " + passportNumber));
    }

    private void ensureEligibleForCheckIn(Flight flight) {
        if (NON_CHECKIN_ELIGIBLE_STATUSES.contains(flight.getStatus())) {
            throw new FlightCheckInNotAllowedException("Cannot check in for flight with status" + flight.getStatus());
        }

        if (flight.getDepartureTime() != null &&
                flight.getDepartureTime().isBefore(LocalDateTime.now())) {
            throw new FlightCheckInNotAllowedException("Cannot check in after scheduled departure time");
        }
    }

    private void ensureSeatAvailability(String flightNumber, Set<String> requestedSeats) {
        if (CollectionUtils.isEmpty(requestedSeats)) {
            return;
        }

        List<FlightCheckInRecord> checkIns = checkInRepository.findByFlightNumber(flightNumber);
        Set<String> taken = checkIns.stream().
                flatMap(r -> r.getSeatNumbers() != null ? r.getSeatNumbers().stream() : Stream.empty())
                .collect(Collectors.toSet());

        for (String seat : requestedSeats) {
            if (taken.contains(seat)) {
                throw new SeatAlreadyTakenException(flightNumber, seat);
            }
        }
    }

    private FlightCheckInRecord initializeCheckInRecord(FlightCheckInRequest request) {
        FlightCheckInRecord checkIn = checkInMapper.toEntity(request);
        checkIn.setId(new ObjectId());
        checkIn.setCheckInTime(Instant.now());
        checkIn.setStatus(CheckInStatus.CHECKED_IN);
        checkIn.setBoardingPassUrl(generateBoardingPassUrl(checkIn.getId()));
        return checkIn;
    }

    private String generateBoardingPassUrl(ObjectId checkInId) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/boarding-pass")
                .path(checkInId.toHexString())
                .toUriString();
    }

    private void processBaggageIfPresent(FlightCheckInRequest request, FlightCheckInRecord checkIn) {
        if (CollectionUtils.isEmpty(request.getBaggages())) {
            checkIn.setBaggageIds(Collections.emptyList());
            checkInRepository.save(checkIn);
            return;
        }

        List<ObjectId> baggageIds = baggageService.createForCheckIn(checkIn.getId(), request.getBaggages());
        checkIn.setBaggageIds(baggageIds);
        checkInRepository.save(checkIn);
    }

    private FlightCheckInResponse buildResponse(FlightCheckInRecord checkIn) {
        FlightCheckInResponse response = checkInMapper.toResponse(checkIn);
        List<BaggageResponse> baggageResponses = baggageService.getBaggageByCheckInId(checkIn.getId());
        response.setBaggages(baggageResponses);
        return response;
    }

    private FlightCheckInRecord findCheckInRecordOrThrow(ObjectId checkInId) {
        return checkInRepository.findById(checkInId).orElseThrow(() -> new CheckInNotFoundException("Check-in not found with id: " + checkInId));
    }

}
