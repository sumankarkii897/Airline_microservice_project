package com.himalayan.controller;

import com.himalayan.payload.request.FlightInstanceRequest;
import com.himalayan.payload.response.ApiResponse;
import com.himalayan.payload.response.FlightInstanceResponse;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.service.FlightInstanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/flight-instances")
@RequiredArgsConstructor
public class FlightInstanceController {
    private final FlightInstanceService flightInstanceService;

    @PostMapping
    public ResponseEntity<ApiResponse<FlightInstanceResponse>> createFlightInstance
            (@RequestHeader("X-Airline-Id") Long airlineId,
         @Valid @RequestBody FlightInstanceRequest flightInstanceRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse
                                .<FlightInstanceResponse>builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message("Flight instance created successfully.")
                                .data(flightInstanceService.createFlightInstance(airlineId, flightInstanceRequest))
                                .timestamp(new Date())
                                .build()
                );
    }
    @GetMapping
   public ResponseEntity<ApiResponse<PageResponse<FlightInstanceResponse>>>
    getFlightInstance(@PageableDefault(size = 10)Pageable pageable){
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<FlightInstanceResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight instance retrived successfully.")
                        .data(flightInstanceService.getFlightInstance(pageable))
                        .timestamp(new Date())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FlightInstanceResponse>> getFlightInstanceById(@PathVariable Long id){
        return ResponseEntity.ok(
                ApiResponse.<FlightInstanceResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight instance retrieved successfully.")
                        .data(flightInstanceService.getFlightInstanceById(id))
                        .timestamp(new Date())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FlightInstanceResponse>> updateFlightInstance
            (
                    @PathVariable Long id,
                    @RequestBody FlightInstanceRequest flightInstanceRequest
            ){
    return ResponseEntity.ok(
            ApiResponse.<FlightInstanceResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Flight instance updated successfully.")
                    .data(flightInstanceService.updateFlightInstance(id, flightInstanceRequest))
                    .timestamp(new Date())
                    .build()
    );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteFlightInstanceById(@PathVariable Long id){
        flightInstanceService.deleteFlightInstance(id);
        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight instance deleted successfully.")
                        .data(null)
                        .timestamp(new Date())
                        .build()
        );
    }

    @GetMapping("/airline")
    public ResponseEntity<ApiResponse<PageResponse<FlightInstanceResponse>>> getByAirlineId
            (
                    @RequestHeader("X-Airline-Id") Long airlineId,
                    @RequestParam(required = false) Long flightId,
                    @RequestParam(required = false)  Long departureAirportId,
                    @RequestParam(required = false)  Long arrivalAirportId,
                    @RequestParam(required = false)LocalDate onDate,
                    @PageableDefault(size = 10) Pageable pageable

                    ){
        return ResponseEntity.ok(
                ApiResponse.<PageResponse<FlightInstanceResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Flight instance retrieved successfully.")
                        .data(flightInstanceService.getByAirlineId(airlineId,departureAirportId,
                                arrivalAirportId,flightId,onDate,pageable))
                        .timestamp(new Date())
                        .build()
        );


    }
}
