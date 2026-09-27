package com.himalayan.repository;

import com.himalayan.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    List<Aircraft> findByAirlineId(Long airlineId);
    boolean existsByCode(String code);
   Optional<Aircraft> findByIdAndAirlineId(Long id, Long airlineId);
   @Modifying
   @Transactional
   long deleteByAirlineId(Long airlineId);
}