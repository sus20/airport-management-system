package com.airport.passenger_checkin_service.mapper;

import com.airport.passenger_checkin_service.domain.dto.request.BaggageRequest;
import com.airport.passenger_checkin_service.domain.dto.response.BaggageResponse;
import com.airport.passenger_checkin_service.domain.entity.BaggageRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BaggageMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "checkInId", ignore = true)
    @Mapping(target = "tagNumber", ignore = true)
    @Mapping(target = "status", ignore = true)
    BaggageRecord toEntity(BaggageRequest request);

    @Mapping(target = "id", expression = "java(record.getId() != null ? record.getId().toHexString() : null)")
    BaggageResponse toResponse(BaggageRecord record);

    List<BaggageResponse> toResponses(List<BaggageRecord> records);
}