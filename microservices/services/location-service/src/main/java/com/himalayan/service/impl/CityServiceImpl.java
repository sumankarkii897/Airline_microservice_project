package com.himalayan.service.impl;

import com.himalayan.exception.AlreadyExistsException;
import com.himalayan.exception.ResourceNotFoundException;
import com.himalayan.mapper.CityMapper;
import com.himalayan.model.City;
import com.himalayan.payload.request.CityRequest;
import com.himalayan.payload.response.CityResponse;
import com.himalayan.payload.response.PageResponse;
import com.himalayan.repository.CityRepository;
import com.himalayan.service.CityService;
import com.himalayan.util.PageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final PageMapper pageMapper;
    @Override
    public CityResponse createCity(CityRequest request)  {
        if(cityRepository.existsByCityCode(request.getCityCode())){
            throw new AlreadyExistsException("City with given code already exist");
        }
        City city = cityMapper.toEntity(request);
     City savedCity = cityRepository.save(city);

        return cityMapper.toResponse(savedCity);
    }

    @Override
    public CityResponse getCityById(Long id) {
        City city = cityRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("City doesn't exist with id : " + id));

        return cityMapper.toResponse(city);
    }

    @Override
    public CityResponse updateCity(Long id, CityRequest request) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City doesn't exist with id : " + id));
        if (request.getCityCode() != null &&
                cityRepository.existsByCityCodeAndIdNot(request.getCityCode(), id)) {
            throw new AlreadyExistsException(
                    "City code '" + request.getCityCode() + "' already exists.");
        }
        City savedCity = cityRepository.save(cityMapper.updateEntity(city, request));
        return cityMapper.toResponse(savedCity);
    }

    @Override
    public void deleteCity(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("City doesn't exist with id : " + id));

      cityRepository.delete(city);


    }

    @Override
    public PageResponse<CityResponse> getAllCities(Pageable pageable) {

        Page<City> cityPage = cityRepository.findAll(pageable);

        Page<CityResponse> responsePage =
                cityPage.map(cityMapper::toResponse);

        return pageMapper.toPageResponse(responsePage);
    }

    @Override
    public PageResponse<CityResponse> searchCities(String keyword, Pageable pageable) {

        Page<City> cityPage = cityRepository.searchByKeyword(keyword, pageable);

       Page<CityResponse> responsePage = cityPage.map(cityMapper::toResponse);
       return pageMapper.toPageResponse(responsePage);
    }

    @Override
    public PageResponse<CityResponse> getCitiesByCountryCode(String countryCode, Pageable pageable) {

        Page<City> cityPage = cityRepository.findByCountryCodeIgnoreCase(countryCode,pageable);


        Page<CityResponse> responsePage= cityPage.map(cityMapper::toResponse);
        return pageMapper.toPageResponse(responsePage);
    }

    @Override
    public boolean existsByCityCode(String cityCode) {

        return cityRepository.existsByCityCode(cityCode);
    }




}
