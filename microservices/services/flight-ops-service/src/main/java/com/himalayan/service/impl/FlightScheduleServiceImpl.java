package com.himalayan.service.impl;

import com.himalayan.enums.FlightStatus;
import com.himalayan.exception.ResourceNotFoundException;
import com.himalayan.mapper.FlightInstanceMapper;
import com.himalayan.mapper.FlightScheduleMapper;
import com.himalayan.model.Flight;
import com.himalayan.model.FlightSchedule;
import com.himalayan.payload.request.FlightInstanceRequest;
import com.himalayan.payload.request.FlightScheduleRequest;
import com.himalayan.payload.response.AirportResponse;
import com.himalayan.payload.response.FlightScheduleResponse;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.repository.FlightRepository;
import com.himalayan.repository.FlightScheduleRepository;
import com.himalayan.service.FlightInstanceService;
import com.himalayan.service.FlightScheduleService;
import com.himalayan.util.PageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightScheduleServiceImpl implements FlightScheduleService {
    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightRepository flightRepository;
    private final FlightScheduleMapper flightScheduleMapper;
    private final FlightInstanceService flightInstanceService;
    private final FlightInstanceMapper flightInstanceMapper;
    private final PageMapper pageMapper;

    @Override
    public FlightScheduleResponse createFlightSchedule(Long airlineId
            , FlightScheduleRequest request) {

        Flight flight = flightRepository.findById(request.getFlightId()).orElseThrow(
                () -> new ResourceNotFoundException("Flight not found with id: " + request.getFlightId())
        );
        if(request.getEndDate().isBefore(request.getStartDate())) {
            throw new ResourceNotFoundException("End date before start date");
        }
        FlightSchedule flightSchedule = flightScheduleMapper.toEntity(request,flight);
        FlightSchedule savedSchedule =flightScheduleRepository.save(flightSchedule);

        List<DayOfWeek> operatingDays =savedSchedule.getOperatingDays();
        LocalDate startDate = savedSchedule.getStartDate();
        LocalDate endDate = savedSchedule.getEndDate();

        FlightInstanceRequest flightInstanceRequest = FlightInstanceRequest.builder()
                .scheduleId(savedSchedule.getId())
                .flightId(flight.getId())
                .arrivalAirportId(flight.getArrivalAirportId())
                .departureAirportId(flight.getDepartureAirportId())
                .status(FlightStatus.SCHEDULED)
                .build();

        for(LocalDate date = startDate;!date.isAfter(endDate);date=date.plusDays(1)) {
            if(operatingDays.contains(date.getDayOfWeek())) {
                flightInstanceRequest.setDepartureDateTime(
                        LocalDateTime.of(date,savedSchedule.getDepartureTime())
                );
                flightInstanceRequest.setArrivalDateTime(
                        LocalDateTime.of(date,savedSchedule.getArrivalTime())
                );
                flightInstanceService.createFlightInstance(airlineId,
                        flightInstanceRequest);

            }
        }
        return convertToFlightScheduleResponse(savedSchedule);
    }

    @Override
    public PageResponse<FlightScheduleResponse> getFlightSchedules(Pageable pageable) {
        Page<FlightSchedule> flightScheduleResponsePage = flightScheduleRepository.findAll(pageable);


        return pageMapper.toPageResponse(flightScheduleResponsePage.map(
                this::convertToFlightScheduleResponse
        ));
    }

    @Override
    public PageResponse<FlightScheduleResponse> getFlightSchedulesById(Long flightId, Pageable pageable) {
        Page<FlightSchedule> flightSchedules =
                flightScheduleRepository.findByFlightId(flightId, pageable);

        if (flightSchedules.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No flight schedules found for flight id: " + flightId
            );
        }


        return pageMapper.toPageResponse( flightSchedules.map(this::convertToFlightScheduleResponse));
    }

    @Override
    public PageResponse<FlightScheduleResponse> getFlightScheduleByAirlines(Long airlineId, Pageable pageable) {
        Page<FlightSchedule> schedules = flightScheduleRepository.findByFlightAirlineId(airlineId, pageable);
        return pageMapper.toPageResponse(  schedules.map(this::convertToFlightScheduleResponse));
    }

    @Override
    public FlightScheduleResponse updateFlightSchedule(Long flightId, FlightScheduleRequest request) {
        FlightSchedule schedule = flightScheduleRepository.findById(flightId).orElseThrow(
                () -> new ResourceNotFoundException("Flight schedule not found with id: " + flightId)
        );
        flightScheduleMapper.updateEntity(request,schedule);
        FlightSchedule updatedSchedule = flightScheduleRepository.save(schedule);
        return convertToFlightScheduleResponse(updatedSchedule);
    }

    @Override
    public void deleteFlightSchedule(Long flightId) {
        FlightSchedule flightSchedule = flightScheduleRepository.findById(flightId).orElseThrow(
                ()-> new ResourceNotFoundException("Flight Schedule Not Found with id: " + flightId)
        );
        flightScheduleRepository.delete(flightSchedule);

    }

    private FlightScheduleResponse convertToFlightScheduleResponse(
            FlightSchedule flightSchedule
    ){
        AirportResponse departureAirport = AirportResponse.builder()
                .id(flightSchedule.getDepartureAirportId())
                .build();

        AirportResponse arrivalAirport = AirportResponse.builder()
                .id(flightSchedule.getArrivalAirportId())
                .build();
        return flightScheduleMapper.toResponse(
                flightSchedule,arrivalAirport,departureAirport

        );
    }
}
