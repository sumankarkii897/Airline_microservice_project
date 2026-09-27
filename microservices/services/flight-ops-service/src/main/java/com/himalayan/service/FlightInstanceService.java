package com.himalayan.service;

import com.himalayan.model.FlightInstance;
import com.himalayan.payload.request.FlightInstanceRequest;
import com.himalayan.payload.response.FlightInstanceResponse;
import com.himalayan.payload.response.PageResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface FlightInstanceService {

    FlightInstanceResponse createFlightInstance(Long airlineId, FlightInstanceRequest request);
    FlightInstanceResponse updateFlightInstance(Long id, FlightInstanceRequest request);
    void deleteFlightInstance(Long id);
    PageResponse<FlightInstanceResponse> getFlightInstance(Pageable pageable);
    FlightInstanceResponse getFlightInstanceById(Long id);
    PageResponse<FlightInstanceResponse> getByAirlineId(Long airlineId,
                                                        Long departureAirportId,
                                                        Long arrivalAirportId,
                                                         Long flightId,
                                                        LocalDate onDate,
                                                        Pageable pageable);

}
