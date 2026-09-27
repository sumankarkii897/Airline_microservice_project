package com.himalayan.embeddable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
//@Entity
@Builder
public class Address {

    private String street;

    private String postalCode;


}
