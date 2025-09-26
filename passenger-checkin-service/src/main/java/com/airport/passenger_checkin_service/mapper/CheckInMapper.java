package com.airport.passenger_checkin_service.mapper;

import com.airport.passenger_checkin_service.domain.FlightCheckInRecord;
import com.airport.passenger_checkin_service.dto.FlightCheckInRequest;
import com.airport.passenger_checkin_service.dto.FlightCheckInResponse;
import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CheckInMapper {

    FlightCheckInRecord toEntity(FlightCheckInRequest request);

    @Mapping(target = "id", expression = "java(checkIn.getId() != null ? checkIn.getId().toHexString() : null)")
    @Mapping(target = "flightId", expression = "java(checkIn.getFlightId() != null ? checkIn.getFlightId().toHexString() : null)")
    FlightCheckInResponse toResponse(FlightCheckInRecord checkIn);

    List<FlightCheckInResponse> toResponses(List<FlightCheckInRecord> entities);

    default ObjectId map(String value) {
        return value == null ? null : new ObjectId(value);
    }

}