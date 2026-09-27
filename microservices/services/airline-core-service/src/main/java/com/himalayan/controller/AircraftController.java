package com.himalayan.controller;

import com.himalayan.enums.AircraftStatus;
import com.himalayan.payload.request.AircraftRequest;
import com.himalayan.payload.request.AircraftStatusRequest;
import com.himalayan.payload.request.AvailabilityRequest;
import com.himalayan.payload.request.UpdateAircraftRequest;
import com.himalayan.payload.response.AircraftResponse;
import com.himalayan.payload.response.ApiResponse;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.service.AircraftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/aircraft")
public class AircraftController {
    private final AircraftService aircraftService;
@PostMapping
    public ResponseEntity<ApiResponse<AircraftResponse>> createAircraft(@Valid @RequestBody AircraftRequest request,
     @RequestHeader("X-User-Id") Long ownerId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<AircraftResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .data(aircraftService.createAircraft(request, ownerId))
                        .timestamp(new Date())
                        .message("Aircraft created successfully.")
                        .build()
        );
    }
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<AircraftResponse>>> getAircrafts(@PageableDefault(page = 0, size = 10)
                                                                                        Pageable pageable) {
    return ResponseEntity.ok(
          ApiResponse.<PageResponse<AircraftResponse>>builder()
                  .statusCode(HttpStatus.OK.value())
                  .message("Aircraft retrieved successfully.")
                  .data(aircraftService.getAirCrafts(pageable))
                  .timestamp(new Date())
                  .build()
    );
    }

@GetMapping("/{id}")

    public ResponseEntity<ApiResponse<AircraftResponse>> getAircraftById( @PathVariable Long id){
        return ResponseEntity.ok(
                ApiResponse.<AircraftResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Aircraft retrieved successfully.")
                        .data(aircraftService.getAirCraftById(id))
                        .timestamp(new Date())
                        .build()
        );
    }

    @GetMapping("/airline/{airlineId}")
    public ResponseEntity<ApiResponse<List<AircraftResponse>>> getAircraftByAirlineId(@PathVariable Long airlineId){
    return ResponseEntity.ok(
            ApiResponse.<List<AircraftResponse>>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Aircraft retrieved successfully.")
                    .data(aircraftService.getAirCraftsByAirlineId(airlineId))
                    .timestamp(new Date())
                    .build()
    );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<AircraftResponse>> updateAircraft(@PathVariable Long id,
                                                                        @Valid
                  @RequestBody UpdateAircraftRequest request, @RequestParam Long ownerId){
    return  ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.<AircraftResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Aircraft updated successfully.")
                    .data(aircraftService.updateAirCraft(request, id, ownerId))
                    .timestamp(new Date())

                    .build()
    );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAircraft(@PathVariable Long id){
        aircraftService.deleteAirCraft(id);
    return ResponseEntity.ok(
            ApiResponse.<Void>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Aircraft deleted successfully.")
                    .timestamp(new Date())
                    .data(null)
                    .build()
    );
    }

    @DeleteMapping("/airline/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAirline(@PathVariable Long id){
    aircraftService.deleteAirCraftByAirlineId(id);
    return ResponseEntity.ok(
            ApiResponse.<Void>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Aircraft deleted successfully.")
                    .timestamp(new Date())
                    .data(null)
                    .build()
    );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AircraftResponse>> updateStatus(@PathVariable Long id, @Valid @RequestBody AircraftStatusRequest request){
    return ResponseEntity.ok(
            ApiResponse.<AircraftResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Aircraft updated successfully.")
                    .timestamp(new Date())
                    .data(aircraftService.changeStatus(id,request.getStatus()))
                    .build()
    );
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<ApiResponse<AircraftResponse>> updateAvailability(@PathVariable Long id, @Valid @RequestBody AvailabilityRequest request){
    return ResponseEntity.ok(
            ApiResponse.<AircraftResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Aircraft updated successfully.")
                    .timestamp(new Date())
                    .data(aircraftService.updateAvailability(id, request.getAvailable()))
                    .build()
    );
    }
}
