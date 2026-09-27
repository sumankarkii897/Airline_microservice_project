package com.himalayan.service;

import com.himalayan.payload.request.AirportRequest;
import com.himalayan.payload.response.AirportResponse;
import com.himalayan.payload.response.PageResponse;
import org.springframework.data.domain.Pageable;

public interface AirportService {

    AirportResponse createAirport(AirportRequest request);
    AirportResponse getAirportById(Long id);

    PageResponse<AirportResponse> getAirports(Pageable pageable);

    AirportResponse updateAirport(Long id, AirportRequest request);

    void deleteAirport(Long id);

    PageResponse<AirportResponse> getAirportByCityId(long cityId, Pageable pageable);



}
