package com.himalayan.controller;

import com.himalayan.payload.request.FlightScheduleRequest;
import com.himalayan.payload.response.ApiResponse;
import com.himalayan.payload.response.FlightScheduleResponse;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.service.FlightScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class FlightScheduleController {
    private final FlightScheduleService flightScheduleService;

    @PostMapping
    public ResponseEntity<ApiResponse<FlightScheduleResponse>> createFlightSchedule(
            @Valid @RequestBody FlightScheduleRequest flightScheduleRequest,
            @RequestHeader("X-Airline-Id") Long airlineId
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<FlightScheduleResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Flight Schedule Created Successfully.")
                        .data(flightScheduleService.createFlightSchedule(airlineId,flightScheduleRequest))
                        .timestamp(new Date())
                        .build()
        );
    }
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<FlightScheduleResponse>>> getFlightSchedules(
            @PageableDefault(size = 10)Pageable pageable
            ){
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<FlightScheduleResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight Schedule Retrived Successfully.")
                        .data(flightScheduleService.getFlightSchedules(pageable))
                        .timestamp(new Date())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PageResponse<FlightScheduleResponse>>> getFlightScheduleById(@PathVariable Long id, Pageable pageable){
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<FlightScheduleResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight Schedule Retrived Successfully.")
                        .data(flightScheduleService.getFlightSchedulesById(id,pageable))
                        .timestamp(new Date())
                        .build()
        );
    }

    @GetMapping("/airline")
    public ResponseEntity<ApiResponse<PageResponse<FlightScheduleResponse>>> getFlightSchedulesByAirline(
            @RequestHeader("X-Airline-Id") Long airlineId,
            @PageableDefault(size = 10)Pageable pageable
    ){
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<FlightScheduleResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight Schedule Retrived Successfully.")
                        .data(flightScheduleService.getFlightScheduleByAirlines(airlineId,pageable))
                        .timestamp(new Date())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FlightScheduleResponse>> updateFlightSchedule(
            @PathVariable Long id,
            @RequestBody FlightScheduleRequest request
    ){
        return ResponseEntity.ok(
                ApiResponse.<FlightScheduleResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight Schedule Updated Successfully.")
                        .data(flightScheduleService.updateFlightSchedule(id,request))
                        .timestamp(new Date())
                        .build()
        );
    }
@DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFlightSchedule(
            @PathVariable Long flightId
){
        flightScheduleService.deleteFlightSchedule(flightId);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight Schedule Deleted Successfully.")
                        .data(null)
                        .timestamp(new Date())
                        .build()
        );
}
}
