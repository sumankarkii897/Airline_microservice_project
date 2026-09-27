package com.himalayan.model;

import com.himalayan.embeddable.Support;
import com.himalayan.enums.AirlineStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 3)
    private String iataCode;
    @Column(nullable = false, unique = true, length = 4)
    private String icaoCode;
    @Column(nullable = false)
    private String name;
@Column(nullable = false)
    private Long ownerId;
    private String alias;

    private String logoUrl;

    private String website;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private AirlineStatus status = AirlineStatus.ACTIVE;

    private String alliance;

    private Long headquarterCityId;

    private Long updatedById;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    @Embedded
    private Support support;

    @OneToMany(mappedBy = "airline", fetch = FetchType.LAZY)
    private List<Aircraft> aircrafts;



}
