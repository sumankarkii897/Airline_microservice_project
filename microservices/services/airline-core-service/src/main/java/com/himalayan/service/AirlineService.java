package com.himalayan.service;

import com.himalayan.enums.AirlineStatus;
import com.himalayan.payload.request.AirlineRequest;
import com.himalayan.payload.request.AirlineStatusUpdateRequest;
import com.himalayan.payload.response.AirlineDropdownItem;
import com.himalayan.payload.response.AirlineResponse;
import com.himalayan.payload.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {

    AirlineResponse createAirline(AirlineRequest airlineRequest, Long ownerId);
    PageResponse<AirlineResponse> getAirlines(Pageable pageable);
    AirlineResponse getAirlineById(Long id);
    AirlineResponse getAirlineByOwner(Long ownerId);
    AirlineResponse updateAirline(Long id,AirlineRequest airlineRequest);
    void deleteAirline(Long id);

    AirlineResponse changeStatus(Long id, AirlineStatusUpdateRequest status);

    List<AirlineDropdownItem> getAirlineDropdown();
}
