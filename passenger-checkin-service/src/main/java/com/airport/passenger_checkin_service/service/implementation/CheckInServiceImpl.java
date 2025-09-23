package com.airport.passenger_checkin_service.service.implementation;

import com.airport.passenger_checkin_service.domain.CheckIn;
import com.airport.passenger_checkin_service.dto.CheckInRequest;
import com.airport.passenger_checkin_service.dto.CheckInResponse;
import com.airport.passenger_checkin_service.exception.DuplicateCheckInException;
import com.airport.passenger_checkin_service.exception.SeatAlreadyTakenException;
import com.airport.passenger_checkin_service.mapper.CheckInMapper;
import com.airport.passenger_checkin_service.repository.CheckInRepository;
import com.airport.passenger_checkin_service.service.CheckInService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckInServiceImpl implements CheckInService {
    private final CheckInRepository checkInRepository;
    private final CheckInMapper checkInMapper;

    @Override
    public CheckInResponse checkIn(CheckInRequest request) {
        validatePassengerNotAlreadyCheckedIn(request);
        validateSeatsAvailability(request);
        CheckIn checkIn = createCheckInEntity(request);
        CheckIn savedCheckIn = checkInRepository.save(checkIn);

        return checkInMapper.toResponse(savedCheckIn);
    }

    @Override
    public CheckInResponse getCheckIn(ObjectId id) {
        return null;
    }

    @Override
    public List<CheckInResponse> getCheckInsForFlight(ObjectId flightId) {
        return List.of();
    }

    @Override
    public CheckInResponse updateSeat(ObjectId checkInId, String seatNumber) {
        return null;
    }

    @Override
    public CheckInResponse updateBaggage(ObjectId checkInId, int baggageCount) {
        return null;
    }

    @Override
    public void cancelCheckIn(ObjectId checkInId) {

    }

    private void validatePassengerNotAlreadyCheckedIn(CheckInRequest request) {
        if (checkInRepository.existsByFlightIdAndPassengerId(request.getFlightId(), request.getPassengerId())) {
            throw new DuplicateCheckInException(request.getFlightId(), request.getPassengerId());
        }
    }

    private void validateSeatsAvailability(CheckInRequest request) {
        if (request.getSeatNumbers() != null && !request.getSeatNumbers().isEmpty()) {
            List<String> takenSeats = request.getSeatNumbers().stream()
                    .filter(seat -> checkInRepository.existsByFlightIdAndSeatNumbersContains(request.getFlightId(), seat))
                    .toList();

            if (!takenSeats.isEmpty()) {
                throw new SeatAlreadyTakenException(request.getFlightId(), takenSeats);
            }
        }
    }

    private CheckIn createCheckInEntity(CheckInRequest request) {
        CheckIn checkIn = checkInMapper.toEntity(request);
        checkIn.setId(new ObjectId());

        String boardingPassUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/boarding-pass/")
                .path(checkIn.getId().toHexString())
                .toUriString();

        checkIn.setBoardingPassUrl(boardingPassUrl);

        return checkIn;
    }
}