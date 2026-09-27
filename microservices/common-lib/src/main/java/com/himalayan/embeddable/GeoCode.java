package com.himalayan.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Builder
public class GeoCode {

    private Double latitude;
    private Double longitude;
}
