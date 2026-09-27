package com.himalayan.repository;

import com.himalayan.model.FlightSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightScheduleRepository extends JpaRepository<FlightSchedule,Long> {

    Page<FlightSchedule> findByFlightAirlineId(Long airlineId, Pageable pageable);
    Page<FlightSchedule> findByFlightId(
            Long flightId,
            Pageable pageable
    );
}
