package com.himalayan.repository;

import com.himalayan.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends JpaRepository<City,Long> {

    boolean existsByCityCodeAndIdNot(String cityCode,Long cityId);
    boolean existsByCityCode(String cityCode);
    Page<City> findByCountryCodeIgnoreCase(String countryCode, Pageable pageable);

    @Query("""
    SELECT c FROM City c
    WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(c.cityCode) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(c.countryCode) like lower(concat('%',:keyword,'%'))
       OR lower (c.countryName) like lower(concat('%',:keyword,'%'))
           OR lower(c.regionCode) like lower(concat('%',:keyword,'%'))        
    """)
    Page<City> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
