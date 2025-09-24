package com.airport.passenger_checkin_service.mapper;

import com.airport.passenger_checkin_service.domain.FlightCheckInRecord;
import com.airport.passenger_checkin_service.dto.FlightCheckInRequest;
import com.airport.passenger_checkin_service.dto.FlightCheckInResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckInMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "checkInTime", ignore = true)
    @Mapping(target = "boardingPassUrl", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    FlightCheckInRecord toEntity(FlightCheckInRequest request);

    @Mapping(target = "id", expression = "java(checkIn.getId() != null ? checkIn.getId().toHexString() : null)")
    FlightCheckInResponse toResponse(FlightCheckInRecord checkIn);

    List<FlightCheckInResponse> toResponses(List<FlightCheckInRecord> entities);
}