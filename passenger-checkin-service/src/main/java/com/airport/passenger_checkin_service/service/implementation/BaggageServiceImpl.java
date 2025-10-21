package com.airport.passenger_checkin_service.service.implementation;

import com.airport.passenger_checkin_service.domain.dto.request.BaggageRequest;
import com.airport.passenger_checkin_service.domain.dto.response.BaggageResponse;
import com.airport.passenger_checkin_service.domain.entity.BaggageRecord;
import com.airport.passenger_checkin_service.domain.entity.FlightCheckInRecord;
import com.airport.passenger_checkin_service.domain.enums.BaggageStatus;
import com.airport.passenger_checkin_service.exception.BaggageNotFoundException;
import com.airport.passenger_checkin_service.exception.CheckInNotFoundException;
import com.airport.passenger_checkin_service.mapper.BaggageMapper;
import com.airport.passenger_checkin_service.repository.BaggageRepository;
import com.airport.passenger_checkin_service.repository.CheckInRepository;
import com.airport.passenger_checkin_service.service.BaggageService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

@Service
@AllArgsConstructor
public class BaggageServiceImpl implements BaggageService {
    private final BaggageRepository baggageRepository;
    private final CheckInRepository checkInRepository;
    private final BaggageMapper baggageMapper;

    @Override
    public List<ObjectId> createForCheckIn(ObjectId checkInId, List< BaggageRequest> baggageRequests) {
        FlightCheckInRecord checkIn = getCheckInById(checkInId);

        List<BaggageRecord> baggageRecords = baggageRequests.stream()
                .map(request -> mapToBaggageEntity(request, checkIn))
                .toList();

        baggageRepository.saveAll(baggageRecords);
        return baggageRecords.stream().map(BaggageRecord::getId).toList();
    }

    @Override
    public BaggageResponse getBaggageById(ObjectId baggageId) {
        return baggageMapper.toResponse(fetchBaggageById(baggageId));
    }

    @Override
    public List<BaggageResponse> getBaggageByCheckInId(ObjectId checkInId) {
        return baggageMapper.toResponses(baggageRepository.findByCheckInId(checkInId));
    }

    @Override
    public BaggageResponse updateStatus(ObjectId baggageId, BaggageStatus status) {
        return updateBaggage(baggageId, rec -> rec.setStatus(status));
    }

    @Override
    public BaggageResponse updateWeight(ObjectId baggageId, double weight) {
        return updateBaggage(baggageId, rec -> rec.setWeight(weight));
    }

    private FlightCheckInRecord getCheckInById(ObjectId checkInId) {
        return checkInRepository.findById(checkInId)
                .orElseThrow(() -> new CheckInNotFoundException("Check-in not found with ID " + checkInId));
    }

    private BaggageRecord fetchBaggageById(ObjectId baggageId) {
        return baggageRepository.findById(baggageId)
                .orElseThrow(() -> new BaggageNotFoundException("Baggage not found with ID " + baggageId));
    }

    private BaggageRecord mapToBaggageEntity(BaggageRequest request, FlightCheckInRecord flightCheckInRecord) {
        BaggageRecord record = baggageMapper.toEntity(request);
        record.setId(new ObjectId());
        record.setCheckInId(flightCheckInRecord.getId());
        record.setTagNumber(generateTagNumber(flightCheckInRecord.getFlightNumber()));
        record.setStatus(BaggageStatus.CHECKED_IN);

        return record;
    }

    private String generateTagNumber(String flightNumber) {
        String suffix = String.valueOf(System.currentTimeMillis() % 10000);
        return flightNumber + "-" + suffix;
    }

    private BaggageResponse updateBaggage(ObjectId baggageId, Consumer<BaggageRecord> updater) {
        BaggageRecord record = fetchBaggageById(baggageId);
        updater.accept(record);
        baggageRepository.save(record);
        return baggageMapper.toResponse(record);
    }
}