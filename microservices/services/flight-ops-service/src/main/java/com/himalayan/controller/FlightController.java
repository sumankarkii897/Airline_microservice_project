package com.himalayan.controller;
import com.himalayan.payload.request.FlightRequest;
import com.himalayan.payload.request.UpdateFlightStatus;
import com.himalayan.payload.response.ApiResponse;
import com.himalayan.payload.response.FlightResponse;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.service.FlightService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<ApiResponse<FlightResponse>> createFlight(@Valid @RequestBody FlightRequest flightRequest,
                                                                    @RequestHeader("Airline-Id") Long airlineId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<FlightResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Flight created successfully.")
                        .data(flightService.createFlight(flightRequest, airlineId))
                        .timestamp(new Date())
                        .build()
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<FlightResponse>>> getFlights(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<FlightResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight fetched successfully.")
                        .data(flightService.getFlights(pageable))
                        .timestamp(new Date())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FlightResponse>> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(
                ApiResponse.<FlightResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight fetched successfully.")
                        .data(flightService.getFlightById(id))
                        .timestamp(new Date())
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFlightById(@PathVariable Long id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight deleted successfully.")
                        .data(null)
                        .timestamp(new Date())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FlightResponse>> updateFlightById(
            @PathVariable Long id,
            @RequestBody FlightRequest flightRequest) {
        return ResponseEntity.ok(
                ApiResponse.<FlightResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight updated successfully.")
                        .data(flightService.updateFlight(flightRequest, id))
                        .timestamp(new Date())
                        .build()
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<FlightResponse>> updateFlightStatus(@PathVariable Long id,
                                                                          @Valid @RequestBody UpdateFlightStatus status) {
        return ResponseEntity.ok(
                ApiResponse.<FlightResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight status updated successfully.")
                        .data(flightService.changeStatus(status, id))
                        .timestamp(new Date())

                        .build()
        );
    }

    @GetMapping("/airline")
    public ResponseEntity<ApiResponse<PageResponse<FlightResponse>>> getFlightsByAirline(
            @RequestHeader("Airline-Id") Long airlineId,
         @RequestParam(required = false) Long departureAirportId,
            @RequestParam(required = false) Long arrivalAirportId,
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                ApiResponse.<PageResponse<FlightResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight fetched successfully.")
                        .data(flightService.getFlightsByAirline(
                                airlineId,
                                departureAirportId,
                                arrivalAirportId,
                                pageable))
                        .timestamp(new Date())
                        .build()
        );
    }


}
