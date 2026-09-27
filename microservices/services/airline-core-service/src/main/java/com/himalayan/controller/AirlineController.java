package com.himalayan.controller;

import com.himalayan.enums.AirlineStatus;
import com.himalayan.payload.request.AirlineRequest;
import com.himalayan.payload.request.AirlineStatusUpdateRequest;
import com.himalayan.payload.response.AirlineDropdownItem;
import com.himalayan.payload.response.AirlineResponse;
import com.himalayan.payload.response.ApiResponse;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.service.AirlineService;
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
@RequestMapping("/airlines")
public class AirlineController {
    private final AirlineService airlineService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<AirlineResponse>> createAirline(@Valid @RequestBody AirlineRequest airlineRequest,
                                                                      @RequestHeader("X-User-Id") Long ownerId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<AirlineResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Airline created successfully")
                        .data(airlineService.createAirline(airlineRequest,ownerId))
                        .timestamp(new Date())
                        .build()
        );

    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<PageResponse<AirlineResponse>>> getAirlines(
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "name"
            ) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<PageResponse<AirlineResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Airlines fetched successfully")
                        .data(airlineService.getAirlines(pageable))
                        .timestamp(new Date())
                        .build()
        );

    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AirlineResponse>> getAirlineById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<AirlineResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Airline fetched successfully")
                        .data(airlineService.getAirlineById(id))
                        .timestamp(new Date())
                        .build()
        );
    }

@GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse<AirlineResponse>> getAirlineByOwnerId(@PathVariable Long ownerId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<AirlineResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Airline fetched successfully")
                        .data(airlineService.getAirlineByOwner(ownerId))
                        .timestamp(new Date())
                        .build()
        );
}

@PutMapping("/{id}")
public ResponseEntity<ApiResponse<AirlineResponse>> updateAirline(@PathVariable Long id, @Valid @RequestBody AirlineRequest airlineRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<AirlineResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Updated Successfully. ")
                        .data(airlineService.updateAirline(id, airlineRequest))
                        .timestamp(new Date())
                        .build()
        );
}

@DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAirline(@PathVariable Long id) {
    airlineService.deleteAirline(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Deleted Successfully. ")
                        .data(null)
                        .timestamp(new Date())
                        .build()
        );
}

@PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<AirlineResponse>> changeStatus(@PathVariable Long id, @RequestBody AirlineStatusUpdateRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<AirlineResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Change Status Successfully. ")
                        .timestamp(new Date())
                        .data(airlineService.changeStatus(id,request))
                        .build()
        );
}
    @GetMapping("/dropdown")
public ResponseEntity<ApiResponse<List<AirlineDropdownItem>>> getAirlineDropdown() {
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<List<AirlineDropdownItem>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Airlines fetched successfully")
                        .data(airlineService.getAirlineDropdown())
                        .timestamp(new Date())
                        .build()
        );
}


}
