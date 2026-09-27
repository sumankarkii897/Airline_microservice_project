package com.himalayan.service;

import com.himalayan.payload.request.FlightScheduleRequest;
import com.himalayan.payload.response.FlightScheduleResponse;
import com.himalayan.payload.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface FlightScheduleService {

    FlightScheduleResponse createFlightSchedule
            (Long airlineId,
             FlightScheduleRequest request);
    PageResponse<FlightScheduleResponse> getFlightSchedules(Pageable pageable);
    PageResponse<FlightScheduleResponse> getFlightSchedulesById(Long flightId, Pageable pageable);
    PageResponse<FlightScheduleResponse> getFlightScheduleByAirlines(Long airlineId, Pageable pageable);
    FlightScheduleResponse updateFlightSchedule(Long flightId, FlightScheduleRequest request);
    void deleteFlightSchedule(Long flightId);
}
