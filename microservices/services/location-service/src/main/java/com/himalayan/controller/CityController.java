package com.himalayan.controller;

import com.himalayan.payload.request.CityRequest;
import com.himalayan.payload.response.ApiResponse;
import com.himalayan.payload.response.CityResponse;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.service.CityService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;

    @PostMapping()
    public ResponseEntity<ApiResponse<CityResponse>> createCity(@Valid @RequestBody CityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.<CityResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("City created successfully")
                        .timestamp(new Date())
                        .data(cityService.createCity(request))
                        .build());
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<PageResponse<CityResponse>>> getAllCities(

//            @PageableDefault(page = 0,size = 10, sort = "name", direction = Sort.Direction.ASC)Pageable pageable
            @RequestParam(defaultValue = "0") @Max(100) int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection

    ){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<PageResponse<CityResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully fetched cities")
                        .timestamp(new Date())
                        .data(cityService.getAllCities(pageable))
                        .build()
        );


    }

    @GetMapping("/country/{countryCode}")
    public ResponseEntity<ApiResponse<PageResponse<CityResponse>>> getCitiesByCountryCode(@PathVariable String countryCode,@PageableDefault(page = 0,size = 10) Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<PageResponse<CityResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully fetched cities")
                        .timestamp(new Date())
                        .data(cityService.getCitiesByCountryCode(countryCode, pageable))
                        .build()
        );
    }

    @GetMapping("/exists/{cityCode}")
    public ResponseEntity<ApiResponse<Boolean>> existsByCityCode(@PathVariable String cityCode){

boolean exists = cityService.existsByCityCode(cityCode);

String message = exists ? "City  exists" : "City does not exist";


        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<Boolean>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(message)
                        .timestamp(new Date())
                        .data(exists)
                        .build()
        );
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PageResponse<CityResponse>>> search(@RequestParam @NotBlank String keyword, @PageableDefault(page = 0,size = 10) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<PageResponse<CityResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully fetched cities")
                        .timestamp(new Date())
                        .data(cityService.searchCities(keyword, pageable))

                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CityResponse>>  getCityById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(
                        ApiResponse.<CityResponse>builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("City with id " + id + " fetched successfully")
                                .timestamp(new Date())
                                .data(cityService.getCityById(id))
                                .build()
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CityResponse>> updateCity(@PathVariable Long id, @Valid @RequestBody CityRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<CityResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("City updated successfully")
                        .timestamp(new Date())
                        .data(cityService.updateCity(id, request))
                        .build()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCity(@PathVariable Long id){
        cityService.deleteCity(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.<Void>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("City deleted successfully")
                        .timestamp(new Date())
                        .data(null)
                        .build()
        );
    }



}
