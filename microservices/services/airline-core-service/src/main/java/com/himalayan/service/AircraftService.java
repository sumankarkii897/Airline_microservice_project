package com.himalayan.service;

import com.himalayan.enums.AircraftStatus;
import com.himalayan.payload.request.AircraftRequest;
import com.himalayan.payload.request.UpdateAircraftRequest;
import com.himalayan.payload.response.AircraftResponse;
import com.himalayan.payload.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AircraftService {

    AircraftResponse createAircraft(AircraftRequest request, Long ownerId);
    PageResponse<AircraftResponse> getAirCrafts(Pageable pageable);
    List<AircraftResponse> getAirCraftsByAirlineId(Long airlineId);
    AircraftResponse getAirCraftById(Long id);
    AircraftResponse updateAirCraft(UpdateAircraftRequest request, Long id, Long ownerId);
    void deleteAirCraft(Long id);
    void deleteAirCraftByAirlineId(Long airlineId);
    AircraftResponse changeStatus(Long id, AircraftStatus status);
    AircraftResponse updateAvailability(Long id, Boolean available);
}
