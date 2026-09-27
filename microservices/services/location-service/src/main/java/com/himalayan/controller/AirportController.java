package com.himalayan.controller;

import com.himalayan.payload.request.AirportRequest;
import com.himalayan.payload.response.AirportResponse;
import com.himalayan.payload.response.ApiResponse;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.repository.AirportRepository;
import com.himalayan.service.AirportService;
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
@RequestMapping("/airports")
@RequiredArgsConstructor
public class AirportController {
    private final AirportService airportService;

    @PostMapping()
    public ResponseEntity<ApiResponse<AirportResponse>> createAirport( @Valid @RequestBody AirportRequest airportRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<AirportResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Airport created successfully")
                        .data(airportService.createAirport(airportRequest))
                        .timestamp(new Date())
                        .build()
        );
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<PageResponse<AirportResponse>>> getAirports(@PageableDefault(page = 0, size = 10) Pageable pageable){
        return  ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<PageResponse<AirportResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Airports fetched successfully")
                        .data(airportService.getAirports(pageable))
                        .timestamp(new Date())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AirportResponse>> getAirportById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<AirportResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Airport with id " + id + " fetched successfully")
                        .data(airportService.getAirportById(id))
                        .timestamp(new Date())
                        .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AirportResponse>> updateAirport(@PathVariable Long id, @Valid @RequestBody  AirportRequest airportRequest){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<AirportResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Airport updated successfully")
                        .data(airportService.updateAirport(id, airportRequest))
                        .timestamp(new Date())

                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAirport(@PathVariable Long id){
        airportService.deleteAirport(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Airport deleted successfully")
                        .data(null)
                        .timestamp(new Date())
                        .build()

        );
    }

    @GetMapping("/city/{id}")
    public ResponseEntity<ApiResponse<PageResponse<AirportResponse>>> getAirportByCityId(@PathVariable long id , @PageableDefault(page=0,size=10) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<PageResponse<AirportResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Airports fetched successfully")
                        .data(airportService.getAirportByCityId(id, pageable))
                        .timestamp(new Date())
                        .build()
        );
    }


}
