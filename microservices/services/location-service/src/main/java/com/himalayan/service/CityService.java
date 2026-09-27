package com.himalayan.service;


import com.himalayan.model.City;
import com.himalayan.payload.request.CityRequest;
import com.himalayan.payload.response.CityResponse;
import com.himalayan.payload.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface CityService {

   CityResponse createCity(CityRequest request);
   CityResponse getCityById(Long id);
   CityResponse updateCity(Long id,CityRequest request);
   void deleteCity(Long id);
   PageResponse<CityResponse> getAllCities(Pageable pageable);
   PageResponse<CityResponse> searchCities(String keyword, Pageable pageable);
   PageResponse<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable);
   boolean existsByCityCode(String cityCode);




}
