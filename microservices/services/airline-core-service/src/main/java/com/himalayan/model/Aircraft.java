package com.himalayan.model;

import com.himalayan.enums.AircraftStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, length = 50)
    private String manufacturer;

    @Column(nullable = false)
    private Integer seatingCapacity;

    @Column(name = "economy_seat")
    private Integer economySeat = 0;

    @Column(name="premium_economy_seats")
    private Integer premiumEconomySeats = 0;

    @Column(name = "business_seats")
    private Integer businessSeats = 0;

    @Column(name = "first_class_seats")
    private Integer firstClassSeats = 0;

    @Column(name="year_of_manufacture")
    private Integer yearOfManufacture;

    @Column(name = "cruising_speed_kmh")
    private Integer cruisingSpeedKmh;

    private Integer rangeKm;

    private Integer maxAltitudeFt;

@CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    private LocalDateTime nextMaintenanceDate;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AircraftStatus status = AircraftStatus.ACTIVE;

    private Boolean isAvailable = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airline_id")
    private Airline airline;

    private Long currentAirportId;

    @CreationTimestamp
    @Column(name="created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name="updated_at", updatable = true)
    private LocalDateTime updatedAt;

    public Integer getTotalSeats(){
        return economySeat + businessSeats+premiumEconomySeats+firstClassSeats;
    }

    public boolean isOperational(){
        return AircraftStatus.ACTIVE==status &&
                Boolean.TRUE.equals(isAvailable);
    }

    public boolean requiresMaintenance(){
        return nextMaintenanceDate!=null &&
                nextMaintenanceDate.isBefore(LocalDateTime.now().plusWeeks(2));
    }


}
