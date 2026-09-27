package com.himalayan.repository;

import com.himalayan.enums.AirlineStatus;
import com.himalayan.model.Airline;
import com.himalayan.payload.response.AirlineDropdownItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {


    Optional<Airline> findByIataCode(String iataCode);
    boolean existsByIataCode(String iataCode);
    boolean existsByIcaoCode(String icaoCode);
    Optional<Airline> findByOwnerId(Long ownerId);
   List<Airline> findByStatus(AirlineStatus status);
    @Query("""
            select new com.himalayan.payload.response.AirlineDropdownItem(
                a.id,
                a.name,
                a.iataCode,
                a.icaoCode,
                a.logoUrl
            )
            from Airline a
            where a.status = :status
            """)
    List<AirlineDropdownItem> findDropdownByStatus(
            AirlineStatus status
    );

}