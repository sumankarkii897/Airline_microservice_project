package com.himalayan.payload.request;
import com.himalayan.enums.AirlineStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirlineRequest {
@NotBlank(message = "IATA code is required")
@Size(min = 3, max = 3, message = "IATA code must be of 3 character")
    private String iataCode;
@NotBlank(message = "ICAO code is required")
@Size(min = 4, max = 4 , message = "ICAO code must be of 4 character")
    private String icaoCode;
@NotBlank(message = "Airline name is required")
    private String name;
private String alias;
@NotBlank(message = "Logo url is required")
    private String logoUrl;
@NotBlank(message = "Website is required")
    private String website;
private String alliance;
//@NotNull(message = "OwnerId is required")
//private Long ownerId;
private Long headquarterCityId;
@Email(message = "Invalid support email")
private String supportEmail;
private String supportPhone;
private String supportHours;
private AirlineStatus status;

}
