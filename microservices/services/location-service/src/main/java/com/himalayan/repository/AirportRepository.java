package com.himalayan.repository;

import com.himalayan.model.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport,Long> {

    Optional<Airport> findByIataCode(String iataCode);

    Page<Airport> findByCityId(Long cityId, Pageable pageable);

    boolean existsByCityId(Long cityId);

    @EntityGraph(attributePaths = {"city"})
    Page<Airport> findAll(Pageable pageable);

}
